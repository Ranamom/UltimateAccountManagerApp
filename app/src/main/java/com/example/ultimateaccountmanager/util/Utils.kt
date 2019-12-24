package com.example.ultimateaccountmanager.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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

    fun clearAllData(context: Context, message: String) {
        val database = AppDatabase.getInstance(context)
        val intent: Intent = Intent(context, SplashScreenActivity::class.java)
        val prefs = SharedPreference(context)

        doAsync {
            database.Dao().deleteAllAccountData()
            database.Dao().deleteAllCharacterData()
        }
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_LONG
        ).show()
        prefs.clearAllPrefsData()
        intent
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        ContextCompat.startActivity(context, intent, Bundle())

    }
}