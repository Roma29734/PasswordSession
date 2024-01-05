package com.pass.word.session

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform