package com.example.ultimateaccountmanager.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.ultimateaccountmanager.R
import com.example.ultimateaccountmanager.models.LoginDetails
import com.example.ultimateaccountmanager.network.NetworkUtils
import com.example.ultimateaccountmanager.util.SharedPreference
import kotlinx.android.synthetic.main.login_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        Timber.plant(Timber.DebugTree())
        edt_login_username.requestFocus()
    }

    override fun onResume() {
        super.onResume()
        val prefs = SharedPreference(context)
        if (prefs.retriveAccountPrefKey() != "uniqueKey") {
            findNavController().navigate(R.id.action_loginFragment_to_accountDetailsFragment)
        }

        btn_login.setOnClickListener {

            val accountNamel = edt_login_username.text.toString()
            val passwordl = edt_login_pass.text.toString()

            val request = NetworkUtils.getEndpoints()

            request.getLoginDetails(accountNamel, passwordl)
                .enqueue(object : Callback<LoginDetails> {
                    override fun onFailure(call: Call<LoginDetails>, t: Throwable) {
                        Toast.makeText(
                            context,
                            "Não foi possivel realizar o login, verifique os dados e tente novamente.",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onResponse(
                        call: Call<LoginDetails>,
                        response: Response<LoginDetails>
                    ) {
                        val responseDetails = response.body()

                        Timber.d(responseDetails.toString())
                        if (response.code() == 200) {
                            prefs.saveAccountUniqueKey(responseDetails!!.msg[0])
                            findNavController().navigate(R.id.action_loginFragment_to_accountDetailsFragment)
                        } else {
                            Toast.makeText(
                                context,
                                "Não foi possivel realizar o login, verifique os dados e tente novamente.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                })

        }
    }

}
