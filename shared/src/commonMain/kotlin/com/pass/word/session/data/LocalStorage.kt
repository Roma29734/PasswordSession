package com.pass.word.session.data

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get

val settings: Settings = Settings()

internal const val keyAuthPass: String = "KEY_AUTH_PASS"
internal const val keyWalletSeed: String = "KEY_WALLET_SEED"

fun getParamsInt(key: String): Int? {
    return settings[key]
}

fun getParamsBoolean(key: String): Boolean? {
    return settings[key]
}

fun getParamsString(key: String): String? {
    return settings[key]
}

fun Int.putToParams(key: String) {
    settings.putInt(key, this)
}

fun Boolean.putToParams(key: String) {
    settings.putBoolean(key, this)
}

fun String.putToParams(key: String) {
    settings.putString(key, this)
}

//8MzWZY044PGVEl3CEXco8HsNrIPE/hSzN1eluEq/LodUqvXArhMUdWyErO5sc3Su3lWdjl1A3LyHuMtrYjG0hzxJAXgGMsiLYZXMx5cn2p7zgV/mcLKw/2ZJxAKifG0
//8MzWZY044PGVEl3CEXco8InE0kZv6lwCFD3sk38zk9O2mLJxBICk5M0ZogWOnzvXxeWTFwPND5f2vEV3h4UVtH8hNXS/ag/2UyWHYziLpWXUaYJgeSLYTwPNCxCCMUa
//8MzWZY044PGVEl3CEXco8InE0kZv6lwCFD3sk38zk9O2mLJxBICk5M0ZogWOnzvXxeWTFwPND5f2vEV3h4UVtH8hNXS/ag/2UyWHYziLpWXUaYJgeSLYTwPNCxCCMUapPMWP6x7OXCb0QWQ2ldBJL0br307zI1f3synWcPjxwVUaOK1I2pv9eaQSA2IfxNu3GZ1Xtn2XBnvmpaG/sNZtQV78MicrbiwMHZL57Vo/p6I=