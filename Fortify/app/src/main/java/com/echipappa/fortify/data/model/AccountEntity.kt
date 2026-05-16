package com.echipappa.fortify.data.model

data class AccountEntity(
    val id: Int = 0,
    val platform: String,
    val icon: String = "🌐",
    val email: String,
    val lastActivity: String = "Recently",
    val tag: String = "NORMAL",
    val createdAt: Long = System.currentTimeMillis()
)