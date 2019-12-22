package com.example.ultimateaccountmanager.ui.accountdetails

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.ultimateaccountmanager.Login
import com.example.ultimateaccountmanager.R
import com.example.ultimateaccountmanager.commons.CharacterAdapter
import com.example.ultimateaccountmanager.database.AppDatabase
import com.example.ultimateaccountmanager.models.Account
import com.example.ultimateaccountmanager.network.NetworkUtils
import com.example.ultimateaccountmanager.splash.SplashScreenActivity
import com.example.ultimateaccountmanager.util.SharedPreference
import kotlinx.android.synthetic.main.account_details_fragment.*
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class AccountDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = AccountDetailsFragment()
    }

    private lateinit var viewModel: AccountDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.account_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(AccountDetailsViewModel::class.java)

        viewModel.refreshData()

        Timber.plant(Timber.DebugTree())


        retriveAccountDataFromDatabase()
        retriveAccountDataFromServer()


//        viewModel.getLiveAccountData().observe(viewLifecycleOwner, Observer { account ->})

        viewModel.getLiveAllCharacterData().observe(viewLifecycleOwner, Observer { characters ->
            recyclerView.adapter = CharacterAdapter(characters, context!!)
        })
    }

    fun retriveAccountDataFromServer() {
        val request = NetworkUtils.getEndpoints()
        val prefs = SharedPreference(context)
        val database = AppDatabase.getInstance(context!!)

        request.getAccountDetails(prefs.retriveAccountPrefKey())
            .enqueue(object : Callback<Account> {
                override fun onFailure(call: Call<Account>, t: Throwable) {
                    doAsync {
                        database.Dao().deleteAllAccountData()
                        database.Dao().deleteAllCharacterData()

                        prefs.clearAllPrefsData()
                    }
                    startActivity(Intent(context, SplashScreenActivity::class.java))
                }

                override fun onResponse(call: Call<Account>, response: Response<Account>) {
                    if (response.code() == 200) {
                        val resultado = response.body()
                        resultado?.let { account ->
                            val premdays = if (account.premdays > 0) {
                                "Premium Account"
                            } else {
                                "Free Account"
                            }
                            txt_account_details_status.text = premdays
                            txt_account_details_name.text = account.name
                            Glide.with(view!!.context).load(R.drawable.animoutfit1)
                                .into(img_account_details_profile)
                        }
                        doAsync {
                            if (resultado != null) {
                                database.Dao().singleAccountInsert(resultado)
                            }
                        }
                    } else {
                        doAsync {
                            database.Dao().deleteAllAccountData()
                            database.Dao().deleteAllCharacterData()

                            prefs.clearAllPrefsData()
                        }
                        startActivity(Intent(context, SplashScreenActivity::class.java))
                    }
                }
            })
    }

    fun retriveAccountDataFromDatabase() {
        val database = AppDatabase.getInstance(context!!)

        doAsync {
            val account = database.Dao().getAccountData()
            val premdays = if (account.premdays > 0) {
                "Premium Account"
            } else {
                "Free Account"
            }
            txt_account_details_status.text = premdays
            txt_account_details_name.text = account.name
            Glide.with(view!!.context).load(R.drawable.animoutfit1)
                .into(img_account_details_profile)
        }

    }

}
