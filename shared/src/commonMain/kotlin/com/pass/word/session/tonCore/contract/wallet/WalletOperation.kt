package com.pass.word.session.tonCore.contract.wallet

import com.pass.word.session.data.getParamsString
import com.pass.word.session.data.keySecretPassKey
import com.pass.word.session.data.model.PasswordListContainer
import com.pass.word.session.tonCore.contract.LiteContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.lighthousegames.logging.logging
import org.ton.api.liteclient.config.LiteClientConfigGlobal
import org.ton.api.liteserver.LiteServerDesc
import org.ton.api.pk.PrivateKeyEd25519
import org.ton.api.pub.PublicKeyEd25519
import org.ton.block.AddrStd
import org.ton.crypto.encoding.base64
import org.ton.lite.client.LiteClient
import org.ton.mnemonic.Mnemonic
import org.ton.tl.ByteString.Companion.toByteString
import com.pass.word.session.tonCore.toAddrString
import com.pass.word.session.tonCore.toKeyPair
import com.pass.word.session.tonCore.toNano
import com.pass.word.session.utilits.ResponseCodState
import com.pass.word.session.utilits.ResponseStatus
import com.pass.word.session.utilits.ResultReadResultFromTonBlock
import com.pass.word.session.utilits.StateBasicResult
import com.pass.word.session.utilits.decrypt
import com.pass.word.session.utilits.encrypt
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class WalletOperation(private val walletSeed: List<String>) {

    // master contract address
    private val contractMasterAddress = "EQBEuRw4qtBLCepCKw6lRZcIpe5G-7ICSI6K1JCCrIPE_DuQ"


    // Init liteClient
    private fun liteClient(): LiteClient {
        return LiteClient(
            liteClientConfigGlobal = LiteClientConfigGlobal(
                liteServers = listServerConfigLite.map {
                    println("liteServerConfiguration - id $it.id.key, ip - ${it.ip}, port = ${it.port}")
                    LiteServerDesc(
                        id = PublicKeyEd25519(base64(it.id.key).toByteString()),
                        ip = it.ip,
                        port = it.port
                    )
                }
            ),
            coroutineContext = Dispatchers.Default,
        )
    }

    private val liteClient = liteClient()


    // Init liteContract and WalletV4R2
    private fun liteContract(): LiteContract {
        return runBlocking {
            LiteContract(liteClient = liteClient)
        }
    }

    // get method:

    private fun getWallet(): WalletV4R2 {
        return runBlocking {

            // m for mainNet
            // t for testNet
            val m = listOf("")

            val t = listOf(
                "alert",
                "grain",
                "ethics",
                "drum",
                "surge",
                "vocal",
                "nation",
                "empower",
                "canyon",
                "guitar",
                "project",
                "diet",
                "field",
                "afford",
                "stumble",
                "joy",
                "solve",
                "inform",
                "amused",
                "angle",
                "fantasy",
                "move",
                "transfer",
                "actress"
            )

            val keyPair = Mnemonic.toKeyPair(walletSeed)

            val privateKey = PrivateKeyEd25519(keyPair.second)
            WalletV4R2(privateKey, 0, liteClient = liteClient)
        }
    }


    suspend fun getWalletAddress(): String? {
        try {
            val wallet = getWallet()
            logging().i("tagLogger") { wallet.address.toAddrString() }
            return wallet.address.toAddrString()
        } catch (e: Exception) {
            logging().i("tagLogger") { "error " + e.message }
            return null
        }
    }


    private suspend fun getAddressPassChildContract(): StateBasicResult<String> {
        try {
            val walletAddress = getWalletAddress()
            return if (walletAddress != null) {
                logging().i("enterSeedPhrase") { "walletAddress - $walletAddress" }
                val result = liteContract().getDataItem(
                    address = AddrStd(contractMasterAddress),
                    addressWallet = AddrStd(walletAddress)
                )
                logging().i("enterSeedPhrase") { "walletAddressRresult - $result" }
                return when (result) {
                    is StateBasicResult.InSuccess -> {
                        StateBasicResult.InSuccess(result.item)
                    }

                    is StateBasicResult.InError -> {
                        StateBasicResult.InError(result.message, result.errorCode)
                    }
                }
            } else {
                StateBasicResult.InError("", ResponseCodState.CD402)
            }
        } catch (e: Exception) {
            logging().i("walletOperation") { "getAddressPassChildContract error " + e.message }
            return StateBasicResult.InError("", ResponseCodState.CD01)
        }
    }

    suspend fun getItemPass(phrase: String): ResultReadResultFromTonBlock {
        try {
            return when (val itemPassChildContractAddress = getAddressPassChildContract()) {
                is StateBasicResult.InSuccess -> {
                    val result =
                        liteContract().getItemPassFromChildContract(
                            AddrStd(
                                itemPassChildContractAddress.item
                            )
                        )
                    if (result != null) {
                        if (result == "null") {
                            return ResultReadResultFromTonBlock.InEmpty
                        }
                        val decryptResult =
                            decrypt(
                                encryptedText = result,
                                secretPhrase = phrase
                            )
                        logging().i("walletOperation") { "getItemPass - resultDecrypt - $decryptResult" }
                        when (decryptResult) {
                            is StateBasicResult.InSuccess -> {
                                if (decryptResult.item == "null") {
                                    return ResultReadResultFromTonBlock.InEmpty
                                }
                                val resultFromObject =
                                    Json.decodeFromString<PasswordListContainer>(decryptResult.item)
                                logging().i("walletOperation") { "resultFromObject - resultFromObject - $resultFromObject" }
                                return ResultReadResultFromTonBlock.InSuccess(resultFromObject.passwordList)
                            }

                            is StateBasicResult.InError -> {
                                ResultReadResultFromTonBlock.InError(
                                    decryptResult.message,
                                    decryptResult.errorCode
                                )
                            }
                        }
                    } else ResultReadResultFromTonBlock.InError("Error in", ResponseCodState.CD01)
                }

                is StateBasicResult.InError -> {
                    ResultReadResultFromTonBlock.InError(
                        itemPassChildContractAddress.message,
                        itemPassChildContractAddress.errorCode
                    )
                }

            }
        } catch (e: Exception) {
            logging().i("walletOperation") { "getItemPass error " + e.stackTraceToString() }
            return ResultReadResultFromTonBlock.InError(e.message.toString(), ResponseCodState.CD01)
        }
    }

    //  send method:
    suspend fun sendNewItemPass(
        newItemModel: PasswordListContainer,
        phrase: String?
    ): StateBasicResult<Boolean> {
        try {
            val savedPhrase = getParamsString(keySecretPassKey)
            val itemPhrase = phrase ?: savedPhrase
            if (itemPhrase != null) {
                val jsonResult = Json.encodeToString(newItemModel)
                logging().i("walletOperation") { "sendNewItemPass jsonResult $jsonResult" }
                val encryptResult = encrypt(text = jsonResult, secretPhrase = itemPhrase)
                logging().i("walletOperation") { "sendNewItemPass jsonResult $encryptResult" }
                val walletAddress = getWalletAddress()
                if (walletAddress != null) {
                    val resultAddress = liteContract().getDataItem(
                        address = AddrStd(contractMasterAddress),
                        addressWallet = AddrStd(walletAddress)
                    )
                    when (resultAddress) {
                        is StateBasicResult.InSuccess -> {
                            when (encryptResult) {
                                is StateBasicResult.InSuccess -> {
                                    val addressChildContract = AddrStd(resultAddress.item)
                                    logging().i("walletOperation") { "sendNewItemPass result ${addressChildContract}" }
                                    val wallet = getWallet()
                                    wallet.transfer(
                                        address = addressChildContract,
                                        amount = 0.01.toNano(),
                                        comment = encryptResult.item
                                    )
                                    return StateBasicResult.InSuccess(true)
                                }

                                is StateBasicResult.InError -> {
                                    return StateBasicResult.InError(
                                        encryptResult.message,
                                        encryptResult.errorCode
                                    )
                                }
                            }
                        }

                        is StateBasicResult.InError -> {
                            return StateBasicResult.InError(
                                resultAddress.message,
                                resultAddress.errorCode
                            )
                        }
                    }
                } else {
                    return StateBasicResult.InError("", ResponseCodState.CD402)
                }
            } else {
                return StateBasicResult.InError("", ResponseCodState.CD402)
            }
        } catch (e: Exception) {
            logging().i("walletOperation") { "sendNewItemPass Exception ${e.stackTraceToString()}" }
            return StateBasicResult.InError("", ResponseCodState.CD00)
        }
    }

}

data class LiteServerId(
    val key: String,
    val type: String,
)

data class LiteServerParams(
    val ip: Int,
    val port: Int,
    val id: LiteServerId
)


private val listServerConfigLite = listOf<LiteServerParams>(
    LiteServerParams(
        ip = 1592601963,
        port = 13833,
        id = LiteServerId(
            key = "QpVqQiv1u3nCHuBR3cg3fT6NqaFLlnLGbEgtBRukDpU=",
            type = "pub.ed25519"
        )
    ),
    LiteServerParams(
        ip = 1097649206,
        port = 29296,
        id = LiteServerId(
            key = "p2tSiaeSqX978BxE5zLxuTQM06WVDErf5/15QToxMYA=",
            type = "pub.ed25519"
        )
    ),
    LiteServerParams(
        ip = 1162057690,
        port = 35939,
        id = LiteServerId(
            key = "97y55AkdzXWyyVuOAn+WX6p66XTNs2hEGG0jFUOkCIo=",
            type = "pub.ed25519"
        )
    ),
    LiteServerParams(
        ip = 1091914380,
        port = 46427,
        id = LiteServerId(
            key = "JhXt7H1dZTgxQTIyGiYV4f9VUARuDxFl/1kVBjLSMB8=",
            type = "pub.ed25519"
        )
    ),
    LiteServerParams(
        ip = 1097633201,
        port = 17439,
        id = LiteServerId(
            key = "0MIADpLH4VQn+INHfm0FxGiuZZAA8JfTujRqQugkkA8=",
            type = "pub.ed25519"
        )
    ),
)