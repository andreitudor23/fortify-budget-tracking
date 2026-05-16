package com.echipappa.fortify.data.repository

import com.echipappa.fortify.data.database.FortifyDatabase
import com.echipappa.fortify.data.model.AccountEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AccountRepository private constructor(private val db: FortifyDatabase) {

    private val _accounts = MutableStateFlow<List<AccountEntity>>(emptyList())
    val allAccounts: Flow<List<AccountEntity>> = _accounts.asStateFlow()

    private val _accountCount = MutableStateFlow(0)
    val accountCount: Flow<Int> = _accountCount.asStateFlow()

    private val _duplicateCount = MutableStateFlow(0)
    val duplicateCount: Flow<Int> = _duplicateCount.asStateFlow()

    private val _atRiskCount = MutableStateFlow(0)
    val atRiskCount: Flow<Int> = _atRiskCount.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        val list = db.getAllAccounts()
        _accounts.value = list
        _accountCount.value = list.size
        _duplicateCount.value = list.count { it.tag == "DUPLICATE" }
        _atRiskCount.value = list.count { it.tag == "RISK" || it.tag == "INACTIVE" }
    }

    suspend fun insert(account: AccountEntity) {
        db.saveAccount(account)
        refresh()
    }

    suspend fun delete(account: AccountEntity) {
        db.deleteAccount(account.id)
        refresh()
    }

    suspend fun update(account: AccountEntity) {
        db.saveAccount(account)
        refresh()
    }

    companion object {
        @Volatile private var INSTANCE: AccountRepository? = null

        fun getInstance(db: FortifyDatabase): AccountRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: AccountRepository(db).also { INSTANCE = it }
            }
    }
}
