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

    internal fun getAllPass(): List<PasswordItemModel> {
        return dbQuery.selectAllPassItemTalbe(::matPassSettings).executeAsList()
    }

    internal fun getOneItemPass(id: Int): PasswordItemModel? {
        return try {
            val result = dbQuery.selectOneItemPassItemTable(id.toLong(), ::matPassSettings).executeAsOne()
            result
        } catch (e: Exception) {
            null
        }
    }

    private fun matPassSettings(
        id: Long,
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
            id = id.toInt()
        )
    }

    internal fun createPass(launches: List<PasswordItemModel>) {
        dbQuery.transaction {
            launches.forEach { launch ->
                insertPass(launch)
            }
        }
    }

    private fun insertPass(launch: PasswordItemModel) {
        dbQuery.insertPassItemTalbe(
            nameItemPassword = launch.nameItemPassword,
            emailOrUserName = launch.emailOrUserName,
            passwordItem = launch.passwordItem,
            changeData = launch.changeData,
            urlSite = launch.urlSite,
            descriptions = launch.descriptions,
        )
    }

    internal fun deleteOneItem(id: Int) {
        dbQuery.deleteOneItem(id = id.toLong())
    }

    internal fun updatePassItem(model: PasswordItemModel) {
        dbQuery.updateOneItem(
            nameItemPassword = model.nameItemPassword,
            emailOrUserName = model.emailOrUserName,
            passwordItem = model.passwordItem,
            changeData = model.changeData,
            urlSite = model.urlSite,
            descriptions = model.descriptions,
            id = model.id.toLong()
        )
    }
}

internal class TonCashDatabase(databaseDriverFactory: DriverFactory) {
    private val database = Database(databaseDriverFactory.createDriver())
    private val dbQuery = database.tonDatabaseQueries
    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllPassItemTalbeTon()
        }
    }

    internal fun getAllPass(): List<PasswordItemModel> {
        return dbQuery.selectAllPassItemTalbeTon(::matPassSettings).executeAsList()
    }

    internal fun getOneItemPass(id: Int): PasswordItemModel? {
        return try {
            val result = dbQuery.selectOneItemPassItemTableTon(id.toLong(), ::matPassSettings).executeAsOne()
            result
        } catch (e: Exception) {
            null
        }
    }

    private fun matPassSettings(
        id: Long,
        nameItemPasswordTon: String,
        emailOrUserNameTon: String,
        passwordItemTon: String,
        changeDataTon: String,
        urlSiteTon: String?,
        descriptionsTon: String?,
    ): PasswordItemModel {
        return PasswordItemModel(
            nameItemPassword = nameItemPasswordTon,
            emailOrUserName = emailOrUserNameTon,
            passwordItem = passwordItemTon,
            changeData = changeDataTon,
            urlSite = urlSiteTon,
            descriptions = descriptionsTon,
            id = id.toInt()
        )
    }

    internal fun createPass(launches: List<PasswordItemModel>) {
        dbQuery.transaction {
            launches.forEach { launch ->
                insertPass(launch)
            }
        }
    }

    private fun insertPass(launch: PasswordItemModel) {
        dbQuery.insertPassItemTalbeTon(
            nameItemPasswordTon = launch.nameItemPassword,
            emailOrUserNameTon = launch.emailOrUserName,
            passwordItemTon = launch.passwordItem,
            changeDataTon = launch.changeData,
            urlSiteTon = launch.urlSite,
            descriptionsTon = launch.descriptions,
        )
    }

    internal fun deleteOneItem(id: Int) {
        dbQuery.deleteOneItemTon(id = id.toLong())
    }

    internal fun updatePassItem(model: PasswordItemModel) {
        dbQuery.updateOneItemTon(
            nameItemPasswordTon = model.nameItemPassword,
            emailOrUserNameTon = model.emailOrUserName,
            passwordItemTon = model.passwordItem,
            changeDataTon = model.changeData,
            urlSiteTon = model.urlSite,
            descriptionsTon = model.descriptions,
            id = model.id.toLong()
        )
    }
}