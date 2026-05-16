package com.echipappa.fortify.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.echipappa.fortify.data.database.FortifyDatabase
import com.echipappa.fortify.data.model.SubscriptionEntity
import com.echipappa.fortify.data.repository.SubscriptionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SubscriptionViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SubscriptionRepository.getInstance(
        FortifyDatabase.getDatabase(application)
    )

    val allSubscriptions = repository.allSubscriptions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalMonthlyCost = repository.totalMonthlyCost
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val unusedSubscriptions = repository.unusedSubscriptions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addSubscription(
        accountId: Int,
        name: String,
        icon: String,
        monthlyCost: Double,
        billingDate: String,
        email: String,
        isUnused: Boolean = false
    ) {
        viewModelScope.launch {
            repository.insert(
                SubscriptionEntity(
                    accountId = accountId,
                    name = name,
                    icon = icon,
                    monthlyCost = monthlyCost,
                    billingDate = billingDate,
                    email = email,
                    isUnused = isUnused
                )
            )
        }
    }

    fun deleteSubscription(subscription: SubscriptionEntity) {
        viewModelScope.launch {
            repository.delete(subscription)
        }
    }

    fun updateSubscription(subscription: SubscriptionEntity) {
        viewModelScope.launch {
            repository.update(subscription)
        }
    }

    fun toggleUnused(subscription: SubscriptionEntity) {
        viewModelScope.launch {
            repository.update(subscription.copy(isUnused = !subscription.isUnused))
        }
    }

    fun reload() {
        repository.refresh()
    }
}
