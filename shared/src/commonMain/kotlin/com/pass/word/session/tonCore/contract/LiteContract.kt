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
import org.ton.tonkotlinusecase.constants.ContractMethods
import com.pass.word.session.tonCore.toAddrString
import com.pass.word.session.tonCore.toSlice

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
    ): VmStack? {
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

        return result
    }


    suspend fun isContractDeployed(address: String): Boolean = isContractDeployed(AddrStd(address))

    suspend fun isContractDeployed(address: AddrStd): Boolean {
        return (liteClient.getAccountState(address).account.value as AccountInfo).storage.state is AccountActive
    }


    // ==== COLLECTION METHODS ====

    suspend fun getItemAddressByIndex(
        collectionAddress: AddrStd,
        index: Int,
        lastBlockId: TonNodeBlockIdExt? = null
    ): MsgAddress? {
        return runSmc(
            collectionAddress,
            ContractMethods.getItemAddressByIndex,
            params = listOf(VmStackValue.of(index)),
            lastBlockId = lastBlockId
        )?.let {
            val stack = it.toMutableVmStack()
            return stack.popSlice().loadTlb(MsgAddress)
        }
    }

    suspend fun getDataItem(
        address: AddrStd,
        lastBlockId: TonNodeBlockIdExt? = null,
        addressWallet: AddrStd
    ): String? {
        println("LLLAsdastBlockId - $lastBlockId")

        val returnedVmStack = runSmc(
            address, "get_address_pass",
            lastBlockId, params = listOf(
                VmStackValue.of(addressWallet.toSlice())
            )
        )
        if (returnedVmStack != null) {
            println("getDataItem depth - ${returnedVmStack.depth}")
            if (returnedVmStack.depth != 5) {

            }
            val resultAddress = returnedVmStack.toMutableVmStack().popSlice().loadTlb(MsgAddress).toAddrString()
            println(
                "getDataItem - $resultAddress"
            )
            println("getDataItem - $returnedVmStack")
            return resultAddress
        } else {
            return null
        }
    }

    suspend fun getItemPassFromChildContract(
        address: AddrStd,
        lastBlockId: TonNodeBlockIdExt? = null,
    ): String? {
        val returnedVmStack = runSmc(
            address, "get_wallet_data",
            lastBlockId, params = listOf()
        )

        return if(returnedVmStack != null) {
            val firstItem = returnedVmStack.toMutableVmStack().popCell().bits.toByteArray().decodeToString()
            val secondItem = returnedVmStack.toMutableVmStack().popCell().refs.first().bits.toByteArray().decodeToString()
            logging().i("liteCOntractRest") { "getItemPassFromChildContract result $firstItem$secondItem" }
            if(secondItem.isEmpty()) {
                return firstItem
            } else {
                return firstItem + secondItem
            }
        } else {
            null
        }
    }

    override fun createDataInit() = Cell.empty()
}
