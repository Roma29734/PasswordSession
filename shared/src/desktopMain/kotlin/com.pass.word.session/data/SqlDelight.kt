package com.pass.word.session.data


import app.cash.sqldelight.db.SqlDriver


actual class DriverFactory() {
    actual fun createDriver(): SqlDriver {
//        return AndroidSqliteDriver(Database.Schema, context, "test.db")
        TODO()
    }
}