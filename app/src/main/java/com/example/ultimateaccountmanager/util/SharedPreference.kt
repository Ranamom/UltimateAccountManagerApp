package com.example.ultimateaccountmanager.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context?) {

    private val UAMPREFS = "com.example.ultimateaccountmanager.account_details"
    private val accountUniqueKey = "uniqueAccountKey"
    private val sharedPref: SharedPreferences =
        context!!.getSharedPreferences(UAMPREFS, Context.MODE_PRIVATE)

    fun saveAccountUniqueKey(key: String) {
        val edit: SharedPreferences.Editor = sharedPref.edit()

        edit.putString(accountUniqueKey, key)
        edit.apply()
    }

    fun retriveAccountPrefKey(): String {
        return sharedPref.getString(accountUniqueKey, "uniqueKey").toString()
    }

    fun clearAllPrefsData() {
        val edit: SharedPreferences.Editor = sharedPref.edit()
        edit.clear()
        edit.apply()
    }
}


