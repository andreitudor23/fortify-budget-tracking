package com.echipappa.fortify.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.echipappa.fortify.data.database.FortifyDatabase
import com.echipappa.fortify.data.model.AccountEntity
import com.echipappa.fortify.data.repository.AccountRepository
import com.echipappa.fortify.data.repository.SubscriptionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AccountViewModel(application: Application) : AndroidViewModel(application) {

    private val db = FortifyDatabase.getDatabase(application)
    private val repository = AccountRepository.getInstance(db)
    private val subscriptionRepository = SubscriptionRepository.getInstance(db)

    val allAccounts = repository.allAccounts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val accountCount = repository.accountCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val duplicateCount = repository.duplicateCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val atRiskCount = repository.atRiskCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun addAccount(
        platform: String,
        icon: String,
        email: String,
        lastActivity: String = "Recently",
        tag: String = "NORMAL"
    ) {
        viewModelScope.launch {
            repository.insert(
                AccountEntity(
                    platform = platform,
                    icon = icon,
                    email = email,
                    lastActivity = lastActivity,
                    tag = tag
                )
            )
        }
    }

    fun deleteAccount(account: AccountEntity) {
        viewModelScope.launch {
            subscriptionRepository.deleteByAccountId(account.id)
            repository.delete(account)
        }
    }

    fun updateAccount(account: AccountEntity) {
        viewModelScope.launch {
            repository.update(account)
        }
    }

    fun reload() {
        repository.refresh()
    }
}
