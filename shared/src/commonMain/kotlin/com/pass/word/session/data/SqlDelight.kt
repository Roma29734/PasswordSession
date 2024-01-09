package com.pass.word.session.data

import app.cash.sqldelight.db.SqlDriver
import com.pass.word.session.cache.Database
import com.pass.word.session.cache.PassItemTable
import com.pass.word.session.data.model.PasswordItemModel

expect class DriverFactory {
    fun createDriver(): SqlDriver
}


internal class PersonalDatabase(databaseDriverFactory: DriverFactory) {
    private val database = Database(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries
    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllPassItemTalbe()
        }
    }
    internal fun getAllLaunches(): List<PasswordItemModel> {
        return dbQuery.selectAllPassItemTalbe(::mapLaunchSelecting).executeAsList()
    }

    private fun mapLaunchSelecting(
        nameItemPassword: String,
        emailOrUserName: String,
        passwordItem: String,
        changeData: String,
        urlSite: String?,
        descriptions: String?,
    ): PasswordItemModel {
        return PasswordItemModel(
            nameItemPassword = nameItemPassword,
            emailOrUserName = emailOrUserName,
            passwordItem = passwordItem,
            changeData = changeData,
            urlSite = urlSite,
            descriptions = descriptions,
        )
    }
    internal fun createLaunches(launches: List<PassItemTable>) {
        dbQuery.transaction {
            launches.forEach { launch ->
                insertLaunch(launch)
            }
        }
    }

    private fun insertLaunch(launch: PassItemTable) {
        dbQuery.insertPassItemTalbe(
            nameItemPassword = launch.nameItemPassword,
            emailOrUserName = launch.emailOrUserName,
            passwordItem = launch.passwordItem,
            changeData = launch.changeData,
            urlSite = launch.urlSite,
            descriptions = launch.descriptions,
        )
    }
}