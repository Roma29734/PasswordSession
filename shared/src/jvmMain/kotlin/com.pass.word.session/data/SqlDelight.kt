package com.pass.word.session.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.pass.word.session.cache.Database
import java.io.File

actual class DriverFactory() {
//    actual fun createDriver(): SqlDriver {
//        return JdbcSqliteDriver("jdbc:sqlite:DataBaseApp.db", schema = Database.Schema)
//    }
actual fun createDriver(): SqlDriver {
    val isDebug = false// or you can use BuildKonfig.isDebug to setup your logic
    val parentFolder = if (isDebug) {
        File(System.getProperty("java.io.tmpdir"))
    } else {
        File(System.getProperty("user.home") + "/PasswordSession")
    }
    if (!parentFolder.exists()) {
        parentFolder.mkdirs()
    }
    val databasePath = if (isDebug) {
        File(System.getProperty("java.io.tmpdir"), "BaseD_Personal.db")
    } else {
        File(parentFolder, "BaseD_Personal.db")
    }
    return JdbcSqliteDriver(url = "jdbc:sqlite:${databasePath.absolutePath}", schema = Database.Schema)
}
}