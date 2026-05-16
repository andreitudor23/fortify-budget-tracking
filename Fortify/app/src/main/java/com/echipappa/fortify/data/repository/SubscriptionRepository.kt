package com.echipappa.fortify.data.repository

import com.echipappa.fortify.data.database.FortifyDatabase
import com.echipappa.fortify.data.model.SubscriptionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SubscriptionRepository private constructor(private val db: FortifyDatabase) {

    private val _subscriptions = MutableStateFlow<List<SubscriptionEntity>>(emptyList())
    val allSubscriptions: Flow<List<SubscriptionEntity>> = _subscriptions.asStateFlow()

    private val _totalMonthlyCost = MutableStateFlow<Double?>(0.0)
    val totalMonthlyCost: Flow<Double?> = _totalMonthlyCost.asStateFlow()

    private val _unusedSubscriptions = MutableStateFlow<List<SubscriptionEntity>>(emptyList())
    val unusedSubscriptions: Flow<List<SubscriptionEntity>> = _unusedSubscriptions.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        val list = db.getAllSubscriptions()
        _subscriptions.value = list
        _totalMonthlyCost.value = list.sumOf { it.monthlyCost }
        _unusedSubscriptions.value = list.filter { it.isUnused }
    }

    suspend fun insert(subscription: SubscriptionEntity) {
        db.saveSubscription(subscription)
        refresh()
    }

    suspend fun delete(subscription: SubscriptionEntity) {
        db.deleteSubscription(subscription.id)
        refresh()
    }

    suspend fun update(subscription: SubscriptionEntity) {
        db.saveSubscription(subscription)
        refresh()
    }

    fun deleteByAccountId(accountId: Int) {
        db.deleteSubscriptionsByAccountId(accountId)
        refresh()
    }

    companion object {
        @Volatile private var INSTANCE: SubscriptionRepository? = null

        fun getInstance(db: FortifyDatabase): SubscriptionRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: SubscriptionRepository(db).also { INSTANCE = it }
            }
    }
}
