package com.pass.word.session.tonCore.contract.wallet

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.runBlocking
import org.lighthousegames.logging.logging
import org.ton.api.liteclient.config.LiteClientConfigGlobal
import org.ton.api.liteserver.LiteServerDesc
import org.ton.api.pk.PrivateKeyEd25519
import org.ton.api.pub.PublicKeyEd25519
import org.ton.crypto.encoding.base64
import org.ton.lite.client.LiteClient
import org.ton.mnemonic.Mnemonic
import org.ton.tl.ByteString.Companion.toByteString
import org.ton.tonkotlinusecase.toAddrString
import org.ton.tonkotlinusecase.toKeyPair

class WalletOperation(private val walletSeed: List<String>) {
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
            coroutineContext = Dispatchers.Default
        )
    }

    private val liteClient = liteClient()

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


//        {
//            "ip": 1959448750,
//            "port": 51281,
//            "id": {
//                "@type": "pub.ed25519",
//                "key": "hyXd2d6yyiD/wirjoraSrKek1jYhOyzbQoIzV85CB98="
//            }
//        },
//        {
//            "ip": 1097633201,
//            "port": 17439,
//            "id": {
//                "@type": "pub.ed25519",
//                "key": "0MIADpLH4VQn+INHfm0FxGiuZZAA8JfTujRqQugkkA8="
//            }
//        },
//        {
//            "ip": 1097649206,
//            "port": 29296,
//            "id": {
//                "@type": "pub.ed25519",
//                "key": "p2tSiaeSqX978BxE5zLxuTQM06WVDErf5/15QToxMYA="
//            }
//        }

private val listServerConfigLite = listOf<LiteServerParams>(
    LiteServerParams(
        ip = 1959448750,
        port = 17439,
        id = LiteServerId(
            key = "hyXd2d6yyiD/wirjoraSrKek1jYhOyzbQoIzV85CB98=",
            type = "pub.ed25519"
        )
    ),
//    LiteServerParams(
//        ip = 1097633201,
//        port = 51281,
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