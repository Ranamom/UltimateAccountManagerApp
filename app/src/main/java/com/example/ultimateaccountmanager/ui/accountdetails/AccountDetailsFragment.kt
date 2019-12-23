package com.example.ultimateaccountmanager.ui.accountdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(AccountDetailsViewModel::class.java)

        Timber.plant(Timber.DebugTree())
        val observeAccount =
            Observer<Account> { account ->

                if (account != null) {

                    val premdays = if (account!!.premdays > 0) {
                        "Premium Account"
                    } else {
                        "Free Account"
                    }
                    txt_account_details_status.text = premdays
                    txt_account_details_name.text = account!!.name
                    Glide.with(view!!.context).load(R.drawable.animoutfit1)
                        .into(img_account_details_profile)
                }

            }
        viewModel.getLiveAccountData().observe(viewLifecycleOwner, observeAccount)
        viewModel.getLiveAllCharacterData().observe(viewLifecycleOwner, Observer { characters ->
            recyclerView.adapter = CharacterAdapter(characters, context!!)
        })
    }
}
