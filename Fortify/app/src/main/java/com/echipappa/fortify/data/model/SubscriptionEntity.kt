package com.echipappa.fortify.data.model

data class SubscriptionEntity(
    val id: Int = 0,
    val accountId: Int = 0,
    val name: String,
    val icon: String = "💳",
    val monthlyCost: Double,
    val billingDate: String,
    val email: String,
    val isUnused: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)