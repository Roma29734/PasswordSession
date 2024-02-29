package com.pass.word.session.tonCore.contract

import kotlinx.coroutines.delay
import org.ton.api.tonnode.TonNodeBlockIdExt
import org.ton.bigint.BigInt
import org.ton.block.AccountActive
import org.ton.block.AccountInfo
import org.ton.block.AddrNone
import org.ton.block.AddrStd
import org.ton.block.MsgAddress
import org.ton.block.MutableVmStack
import org.ton.block.VmStack
import org.ton.block.VmStackValue
import org.ton.cell.Cell
import org.ton.cell.CellBuilder
import org.ton.cell.storeRef
import org.ton.lite.client.LiteClient
import org.ton.tlb.loadTlb
import org.ton.tonkotlinusecase.LiteServerAccountId
import org.ton.tonkotlinusecase.constants.ContractMethods
import org.ton.tonkotlinusecase.loadRemainingBits
import org.ton.tonkotlinusecase.loadRemainingBitsAll
import org.ton.tonkotlinusecase.toAddrString
import org.ton.tonkotlinusecase.toSlice

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
        return if (lastBlockId != null && params.isNotEmpty())
            liteClient.runSmcMethod(
                address = LiteServerAccountId(address),
                methodName = method,
                blockId = lastBlockId,
                params = params
            )
        else if (lastBlockId != null)
            liteClient.runSmcMethod(
                address = LiteServerAccountId(address),
                methodName = method,
                blockId = lastBlockId
            )
        else if (params.isNotEmpty())
            liteClient.runSmcMethod(
                address = LiteServerAccountId(address),
                methodName = method,
                params = params
            )
        else
            liteClient.runSmcMethod(address = LiteServerAccountId(address), methodName = method)
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
        while (result == null && retryCount < 4) {
            if (retryCount > 0) {
                delay(100)
//                println("Retry $retryCount $method for ${address.toAddrString()}")
            }
            try {
                result = runSmcRetry(address, method, lastBlockId, params)
            } catch (e: Exception) {
                ex = e
            }
            retryCount++
        }
        if (result == null && ex != null) {
            //error message
//            logger.warn("Error in $method for ${address.toAddrString()}")
//            logger.warn(ex.message)
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
    ) {
        runSmc(
            address, "get_address_pass",
            lastBlockId, params = listOf(
//                VmStackValue.of(),
                VmStackValue.of(addressWallet.toSlice())
            )
        )?.let { stact ->
            println("getDataItem depth - ${stact.depth}")
            if (stact.depth != 5) {

            }
//            liteClient.sendMessage(body = Message(body = Either(x =2, y = 1)))
//            stact.toMutableVmStack()
            println(
                "getDataItem - ${
                    stact.toMutableVmStack().popSlice().loadTlb(MsgAddress).toAddrString()
                }"
            )
            println("getDataItem - $stact")
        }
    }


    override fun createDataInit() = Cell.empty()

}