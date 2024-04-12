package com.pass.word.session.tonCore.contract

import kotlinx.coroutines.delay
import org.lighthousegames.logging.logging
import org.ton.api.tonnode.TonNodeBlockIdExt
import org.ton.block.AccountActive
import org.ton.block.AccountInfo
import org.ton.block.AddrStd
import org.ton.block.MsgAddress
import org.ton.block.VmStack
import org.ton.block.VmStackValue
import org.ton.cell.Cell
import org.ton.lite.client.LiteClient
import org.ton.tlb.loadTlb
import com.pass.word.session.tonCore.LiteServerAccountId
import com.pass.word.session.tonCore.toAddrString
import com.pass.word.session.tonCore.toSlice
import com.pass.word.session.utilits.ResponseCodState
import com.pass.word.session.utilits.StateBasicResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull

open class LiteContract(
    override val liteClient: LiteClient
) : RootContract(liteClient) {

    override val sourceCode: Cell
        get() = TODO("Not yet implemented")


    private suspend fun runSmcRetry(
        address: AddrStd,
        method: String,
        lastBlockId: TonNodeBlockIdExt? = null,
        params: List<VmStackValue> = listOf()
    ): VmStack {
        return if (lastBlockId != null && params.isNotEmpty()) {
            println("runSmcRetryLogs firtIF - $lastBlockId $params")
            return liteClient.runSmcMethod(
                address = LiteServerAccountId(address),
                methodName = method,
                blockId = lastBlockId,
                params = params
            )
        } else if (lastBlockId != null) {
            println("runSmcRetryLogs secondIF - $lastBlockId $params")
            return liteClient.runSmcMethod(
                address = LiteServerAccountId(address),
                methodName = method,
                blockId = lastBlockId
            )
        } else if (params.isNotEmpty()) {

            println("runSmcRetryLogs thirdIF - $lastBlockId $params ${LiteServerAccountId(address)}")
            return liteClient.runSmcMethod(
                address = LiteServerAccountId(address),
                methodName = method,
                params = params
            )
        } else {
            println("runSmcRetryLogs fourIF - $lastBlockId $params")
            liteClient.runSmcMethod(address = LiteServerAccountId(address), methodName = method)
        }
    }

    // I do not use AOP or spring-retry because of current class-architecture
    // we need to make retryable calls from other class that must be injected here
    // but this class is the Root of all contracts, so it will not be very handful
    // --
    // also for make aspects work we need to make everything open: logger, liteClient etc
    // probably liteClient must be removed from here somehow at all
    suspend fun runSmc(
        address: AddrStd,
        method: String,
        lastBlockId: TonNodeBlockIdExt? = null,
        params: List<VmStackValue> = listOf()
    ): Pair<VmStack?, Exception?> {
        var result: VmStack? = null
        var retryCount = 0
        var ex: Exception? = null
        logging().i("runSmcMethod") { "result firstEs - $result, retryCount - $retryCount" }
        while (result == null && retryCount < 4) {
            if (retryCount > 0) {
                delay(100)
//                println("Retry $retryCount $method for ${address.toAddrString()}")
            }
            try {
                val predREsult = runSmcRetry(address, method, lastBlockId, params)
                logging().i("runSmcMethod") { "result try block - $predREsult, retryCount - $retryCount" }
                result = predREsult
            } catch (e: Exception) {
                logging().i("runSmcMethod") { "result errorBlock - ${e.message}, retryCount - $retryCount" }
                ex = e
            }
            retryCount++
        }
        if (result == null && ex != null) {
            logging().i("runSmcMethod") { "result ifChecked - $result, retryCount - $retryCount" }
            println("Error in $method for ${address.toAddrString()}")
            println(ex.message)
        }
//        }.onFailure {
//            logger.warn("Error in $method for ${address.toAddrString()}")
//            logger.warn(it.message)
//        }.getOrNull()

        return Pair(result, ex)
    }


    suspend fun isContractDeployed(address: String): Boolean = isContractDeployed(AddrStd(address))

    suspend fun isContractDeployed(address: AddrStd): Boolean {
        return (liteClient.getAccountState(address).account.value as AccountInfo).storage.state is AccountActive
    }


    // ==== COLLECTION METHODS ====

    suspend fun getAddressPassContact(
        address: AddrStd,
        lastBlockId: TonNodeBlockIdExt? = null,
        addressWallet: AddrStd
    ): StateBasicResult<String> {
        println("LLLAsdastBlockId - $lastBlockId")

        val returnedVmStack = withTimeoutOrNull(15000L) {
                logging().i("liteCOntractRest") { "returnedVmStack вызванно" }
                runSmc(
                    address, "get_address_pass",
                    lastBlockId, params = listOf(
                        VmStackValue.of(addressWallet.toSlice())
                    )
                )
        }
        logging().i("liteCOntractRest") { "returnedVmStack $returnedVmStack" }
        if (returnedVmStack != null) {
            val vmStack = returnedVmStack.first
            val ex = returnedVmStack.second

            return if (vmStack != null && ex == null) {
                println("getAddressPassContact depth - ${vmStack.depth}")
                if (vmStack.depth != 5) {

                }
                val resultAddress =
                    vmStack.toMutableVmStack().popSlice().loadTlb(MsgAddress)
                        .toAddrString()
                println(
                    "getAddressPassContact - $resultAddress"
                )
                println("getAddressPassContact - $returnedVmStack")
                StateBasicResult.InSuccess(resultAddress)
            } else if (ex != null) {
                logging().i("liteCOntractRest") { "getAddressPassContact ex  ${ex.message}" }
                logging().i("liteCOntractRest") { "getAddressPassContact ex stack  ${ex.stackTraceToString()}" }
                if (ex is RuntimeException) {
                    return StateBasicResult.InError("in getData item", ResponseCodState.CD01)
                }
                if (ex is org.ton.api.exception.TvmException) {
                    return StateBasicResult.InError("in getData item", ResponseCodState.CD404)
                }
                StateBasicResult.InError("in getData item", ResponseCodState.CD401)
            } else {
                StateBasicResult.InError("in getData item", ResponseCodState.CD401)
            }
        } else {
            logging().i("liteCOntractRest") { "getAddressPassContact err cd02" }
            return StateBasicResult.InError("in getData item", ResponseCodState.CD02)
        }
    }

    suspend fun getItemPassFromChildContract(
        address: AddrStd,
        lastBlockId: TonNodeBlockIdExt? = null,
    ): StateBasicResult<String> {

        val returnedVmStack = withTimeoutOrNull(15000L) {
            runSmc(
                address, "get_wallet_data",
                lastBlockId, params = listOf()
            )
        }

        if (returnedVmStack != null) {
            return if (returnedVmStack.first != null) {
                if (returnedVmStack.first!!.depth != 5) {
                    logging().i("liteCOntractRest") { "decoreResult  dept !=5" }
                }
                val stack = returnedVmStack.first!!.toMutableVmStack().popCell()
                val decodeResult = decodeCellToString(stack)

                logging().i("liteCOntractRest") { "decoreResult  $decodeResult" }
                if (decodeResult == null) {
                    return StateBasicResult.InError("in getData item", ResponseCodState.CD00)
                }
                return StateBasicResult.InSuccess(decodeResult)
            } else {
                if (returnedVmStack.second is org.ton.api.exception.TvmException) {
                    logging().i("liteCOntractRest") { "getItemPassFromChildContract  message deploy not " }
                    return StateBasicResult.InError("in getData item", ResponseCodState.CD404)
                }
                if (returnedVmStack.second is RuntimeException) {
                    return StateBasicResult.InError("in getData item", ResponseCodState.CD01)
                }
                return StateBasicResult.InError("in getData item", ResponseCodState.CD00)
            }
        } else {
            return StateBasicResult.InError("in getData item", ResponseCodState.CD02)
        }
    }


    private suspend fun decodeCellToString(itemCell: Cell): String? {
        return try {
            var result = itemCell.bits.toByteArray().decodeToString()
            var refs = itemCell.refs

            while (refs.isNotEmpty()) {
                val currentItemString = refs.last().bits.toByteArray().decodeToString()
                result += currentItemString
                refs = refs.firstOrNull()?.refs ?: emptyList()
            }

            result
        } catch (e: Exception) {
            logging().i("liteCOntractRest") { "decoreResult error  ${e.message}" }
            null
        }
    }


    override fun createDataInit() = Cell.empty()
}
