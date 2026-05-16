package com.echipappa.fortify.data.database

import android.content.Context
import android.content.SharedPreferences
import com.echipappa.fortify.data.model.AccountEntity
import com.echipappa.fortify.data.model.SubscriptionEntity
import org.json.JSONArray
import org.json.JSONObject

class FortifyDatabase(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("fortify_db", Context.MODE_PRIVATE)

    init {
        if (prefs.getInt("db_version", 0) < 2) {
            prefs.edit()
                .remove("subscriptions")
                .remove("accounts")
                .putInt("db_version", 2)
                .apply()
        }
    }

    // SUBSCRIPTIONS
    fun getAllSubscriptions(): List<SubscriptionEntity> {
        val json = prefs.getString("subscriptions", "[]") ?: "[]"
        val array = JSONArray(json)
        val list = mutableListOf<SubscriptionEntity>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            list.add(
                SubscriptionEntity(
                    id = obj.getInt("id"),
                    accountId = obj.optInt("accountId", 0),
                    name = obj.getString("name"),
                    icon = obj.getString("icon"),
                    monthlyCost = obj.getDouble("monthlyCost"),
                    billingDate = obj.getString("billingDate"),
                    email = obj.getString("email"),
                    isUnused = obj.getBoolean("isUnused")
                )
            )
        }
        return list
    }

    fun saveSubscription(subscription: SubscriptionEntity) {
        val list = getAllSubscriptions().toMutableList()
        val existing = list.indexOfFirst { it.id == subscription.id }
        if (existing >= 0) list[existing] = subscription
        else list.add(subscription.copy(id = System.currentTimeMillis().toInt()))
        saveSubscriptions(list)
    }

    fun deleteSubscription(id: Int) {
        val list = getAllSubscriptions().filter { it.id != id }
        saveSubscriptions(list)
    }

    fun deleteSubscriptionsByAccountId(accountId: Int) {
        val list = getAllSubscriptions().filter { it.accountId != accountId }
        saveSubscriptions(list)
    }

    private fun saveSubscriptions(list: List<SubscriptionEntity>) {
        val array = JSONArray()
        list.forEach { sub ->
            val obj = JSONObject()
            obj.put("id", sub.id)
            obj.put("accountId", sub.accountId)
            obj.put("name", sub.name)
            obj.put("icon", sub.icon)
            obj.put("monthlyCost", sub.monthlyCost)
            obj.put("billingDate", sub.billingDate)
            obj.put("email", sub.email)
            obj.put("isUnused", sub.isUnused)
            array.put(obj)
        }
        prefs.edit().putString("subscriptions", array.toString()).apply()
    }

    // ACCOUNTS
    fun getAllAccounts(): List<AccountEntity> {
        val json = prefs.getString("accounts", "[]") ?: "[]"
        val array = JSONArray(json)
        val list = mutableListOf<AccountEntity>()
        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)
            list.add(
                AccountEntity(
                    id = obj.getInt("id"),
                    platform = obj.getString("platform"),
                    icon = obj.getString("icon"),
                    email = obj.getString("email"),
                    lastActivity = obj.getString("lastActivity"),
                    tag = obj.getString("tag")
                )
            )
        }
        return list
    }

    fun saveAccount(account: AccountEntity) {
        val list = getAllAccounts().toMutableList()
        val existing = list.indexOfFirst { it.id == account.id }
        if (existing >= 0) list[existing] = account
        else list.add(account.copy(id = System.currentTimeMillis().toInt()))
        saveAccounts(list)
    }

    fun deleteAccount(id: Int) {
        val list = getAllAccounts().filter { it.id != id }
        saveAccounts(list)
    }

    private fun saveAccounts(list: List<AccountEntity>) {
        val array = JSONArray()
        list.forEach { acc ->
            val obj = JSONObject()
            obj.put("id", acc.id)
            obj.put("platform", acc.platform)
            obj.put("icon", acc.icon)
            obj.put("email", acc.email)
            obj.put("lastActivity", acc.lastActivity)
            obj.put("tag", acc.tag)
            array.put(obj)
        }
        prefs.edit().putString("accounts", array.toString()).apply()
    }

    // AUTH
    fun registerUser(name: String, email: String, password: String): Boolean {
        val existingEmail = prefs.getString("user_email", null)
        if (existingEmail == email) return false
        prefs.edit()
            .putString("user_name", name)
            .putString("user_email", email)
            .putString("user_password", password)
            .apply()
        return true
    }

    fun getUserName(): String {
        return prefs.getString("user_name", "") ?: ""
    }

    fun loginUser(email: String, password: String): Boolean {
        val savedEmail = prefs.getString("user_email", null)
        val savedPassword = prefs.getString("user_password", null)
        return savedEmail == email && savedPassword == password
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("is_logged_in", false)
    }

    fun setLoggedIn(value: Boolean) {
        prefs.edit().putBoolean("is_logged_in", value).apply()
    }

    fun getLoggedInEmail(): String {
        return prefs.getString("user_email", "") ?: ""
    }

    fun logOut() {
        prefs.edit().putBoolean("is_logged_in", false).apply()
    }

    fun resetAllData() {
        prefs.edit()
            .remove("subscriptions")
            .remove("accounts")
            .remove("user_name")
            .remove("user_email")
            .remove("user_password")
            .putBoolean("is_logged_in", false)
            .apply()
    }

    companion object {
        @Volatile
        private var INSTANCE: FortifyDatabase? = null

        fun getDatabase(context: Context): FortifyDatabase {
            return INSTANCE ?: synchronized(this) {
                FortifyDatabase(context).also { INSTANCE = it }
            }
        }
    }
}