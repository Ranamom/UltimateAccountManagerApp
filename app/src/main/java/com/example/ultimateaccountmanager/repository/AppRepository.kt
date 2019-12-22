package com.example.ultimateaccountmanager.repository

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.example.ultimateaccountmanager.database.AppDatabase
import com.example.ultimateaccountmanager.models.Account
import com.example.ultimateaccountmanager.models.Character
import com.example.ultimateaccountmanager.network.NetworkUtils
import com.example.ultimateaccountmanager.splash.SplashScreenActivity
import com.example.ultimateaccountmanager.util.SharedPreference
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepository(context: Context) {

    val database = AppDatabase.getInstance(context)
    val prefs = SharedPreference(context)
    val intent: Intent = Intent(context, SplashScreenActivity::class.java)
    val cont: Context = context

    fun getLiveAllCharacters() = database.Dao().getLiveAllCharacterData()
    fun getLiveAccountData() = database.Dao().getLiveAccountData()

    fun retriveAccountDataFromServer() {
        val request = NetworkUtils.getEndpoints()

        request.getAccountDetails(prefs.retriveAccountPrefKey())
            .enqueue(object : Callback<Account> {
                override fun onFailure(call: Call<Account>, t: Throwable) {

                }

                override fun onResponse(call: Call<Account>, response: Response<Account>) {
                    if (response.code() == 200) {
                        val resultado = response.body()
                        doAsync {
                            resultado?.let { database.Dao().singleAccountInsert(it) }
                        }
                    } else {
                        doAsync {
                            database.Dao().deleteAllAccountData()
                            database.Dao().deleteAllCharacterData()

                            prefs.clearAllPrefsData()
                        }
                    }
                }
            })
    }

    fun retriveCharacterDataFromServer() {
        val request = NetworkUtils.getEndpoints()

        request.getCharactersDetails(prefs.retriveAccountPrefKey())
            .enqueue(object : Callback<List<Character>> {
                override fun onFailure(call: Call<List<Character>>, t: Throwable) {
                    doAsync {
                        database.Dao().deleteAllAccountData()
                        database.Dao().deleteAllCharacterData()

                        prefs.clearAllPrefsData()
                    }
                    startActivity(cont, intent, Bundle())
                }

                override fun onResponse(
                    call: Call<List<Character>>,
                    response: Response<List<Character>>
                ) {
                    val result = response.body()
                    if (response.code() == 200) {
                        result?.forEach {
                            doAsync {
                                database.Dao().singleCharacterInsert(it)
                            }
                        }
                    } else {
                        doAsync {
                            database.Dao().deleteAllAccountData()
                            database.Dao().deleteAllCharacterData()

                            prefs.clearAllPrefsData()
                        }
                        startActivity(cont, intent, Bundle())
                    }
                }
            })
    }


}