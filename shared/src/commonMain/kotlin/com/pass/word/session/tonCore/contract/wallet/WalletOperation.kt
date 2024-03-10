package com.pass.word.session.tonCore.contract.wallet

import androidx.compose.runtime.key
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
import com.pass.word.session.utilits.KeySecretPhrase
import com.pass.word.session.utilits.decrypt
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


    private suspend fun getAddressPassChildContract(): String? {
        try {
            val walletAddress = getWalletAddress()
            return if (walletAddress != null) {
                logging().i("enterSeedPhrase") { "walletAddress - $walletAddress" }
                val result = liteContract().getDataItem(
                    address = AddrStd(contractMasterAddress),
                    addressWallet = AddrStd("EQDDp_elzjex41uOyzGPNRKy8ysk44-2YxhLj4xg29xJsq_6")
                )
                logging().i("enterSeedPhrase") { "walletAddressRresult - $result" }
                if (result != null) {
                    liteContract().getItemPassFromChildContract(AddrStd(result))
                }
                return result
            } else {
//             returned Null
                null
            }
        } catch (e: Exception) {
            logging().i("walletOperation") { "getAddressPassChildContract error " + e.message }
            return null
        }
    }


    suspend fun getItemPass(): PasswordListContainer? {
        try {
            val itemPassChildContractAddress = getAddressPassChildContract()
            return if (itemPassChildContractAddress != null) {
                val result = liteContract().getItemPassFromChildContract(AddrStd(itemPassChildContractAddress))
                if(result != null) {
                    val decryptResult = decrypt(encryptedText = result, secretPhrase = KeySecretPhrase)
                    logging().i("walletOperation") { "getItemPass - resultDecrypt - $decryptResult" }
                    val resultFromObject = Json.decodeFromString<PasswordListContainer>(decryptResult)
                    logging().i("walletOperation") { "resultFromObject - resultFromObject - $resultFromObject" }
                    resultFromObject
                } else null
            } else {
                null
            }
        } catch (e: Exception) {
            logging().i("walletOperation") { "getItemPass error " + e.message }
            return null
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


//"liteservers": [
//{
//    "ip": 1959448750,
//    "port": 51281,
//    "id": {
//    "@type": "pub.ed25519",
//    "key": "hyXd2d6yyiD/wirjoraSrKek1jYhOyzbQoIzV85CB98="
//}
//},
//{
//    "ip": 1097633201,
//    "port": 17439,
//    "id": {
//    "@type": "pub.ed25519",
//    "key": "0MIADpLH4VQn+INHfm0FxGiuZZAA8JfTujRqQugkkA8="
//}
//},
//{
//    "ip": 1097649206,
//    "port": 29296,
//    "id": {
//    "@type": "pub.ed25519",
//    "key": "p2tSiaeSqX978BxE5zLxuTQM06WVDErf5/15QToxMYA="
//}
//}
//],

//	{
//			"ip": 1592601963,
//			"port": 13833,
//			"id": {
//				"@type": "pub.ed25519",
//				"key": "QpVqQiv1u3nCHuBR3cg3fT6NqaFLlnLGbEgtBRukDpU="
//			}
//		},
//		{
//			"ip": 1097649206,
//			"port": 29296,
//			"id": {
//				"@type": "pub.ed25519",
//				"key": "p2tSiaeSqX978BxE5zLxuTQM06WVDErf5/15QToxMYA="
//			}
//		},
//		{
//			"ip": 1162057690,
//			"port": 35939,
//			"id": {
//				"@type": "pub.ed25519",
//				"key": "97y55AkdzXWyyVuOAn+WX6p66XTNs2hEGG0jFUOkCIo="
//			}
//		},


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
//    LiteServerParams(
//        ip = 1959448750,
//        port = 51281,
//        id = LiteServerId(
//            key = "hyXd2d6yyiD/wirjoraSrKek1jYhOyzbQoIzV85CB98=",
//            type = "pub.ed25519"
//        )
//    ),
//    LiteServerParams(
//        ip = 1097633201,
//        port = 17439,
//        id = LiteServerId(
//            key = "0MIADpLH4VQn+INHfm0FxGiuZZAA8JfTujRqQugkkA8=",
//            type = "pub.ed25519"
//        )
//    ),
//    LiteServerParams(
//        ip = 1097649206,
//        port = 29296,
//        id = LiteServerId(
//            key = "p2tSiaeSqX978BxE5zLxuTQM06WVDErf5/15QToxMYA=",
//            type = "pub.ed25519"
//        )
//    ),
)