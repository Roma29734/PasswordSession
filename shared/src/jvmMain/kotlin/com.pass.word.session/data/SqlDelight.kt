package com.pass.word.session.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.pass.word.session.cache.Database

actual class DriverFactory() {
    actual fun createDriver(): SqlDriver {
        return JdbcSqliteDriver("jdbc:sqlite:DataBaseApp.db", schema = Database.Schema)
    }
}