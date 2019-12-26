package com.example.ultimateaccountmanager.util

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.example.ultimateaccountmanager.database.AppDatabase
import com.example.ultimateaccountmanager.splash.SplashScreenActivity
import org.jetbrains.anko.doAsync

object Utils {

    fun hideSoftKeyBoard(context: Context, view: View) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun checkInternetStatus(context: Context): Boolean {
        val conMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork: NetworkInfo? = conMgr.activeNetworkInfo

        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun clearAllData(context: Context) {
        val database = AppDatabase.getInstance(context)
        val intent = Intent(context, SplashScreenActivity::class.java)
        val prefs = SharedPreference(context)

        doAsync {
            database.Dao().deleteAllAccountData()
            database.Dao().deleteAllCharacterData()
        }
        prefs.clearAllPrefsData()
        intent
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        ContextCompat.startActivity(context, intent, Bundle())

    }
}