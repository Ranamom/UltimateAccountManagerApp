package com.example.ultimateaccountmanager.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.ultimateaccountmanager.R
import com.example.ultimateaccountmanager.models.LoginDetails
import com.example.ultimateaccountmanager.network.NetworkUtils
import com.example.ultimateaccountmanager.util.SharedPreference
import com.example.ultimateaccountmanager.util.Utils
import kotlinx.android.synthetic.main.login_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class LoginFragment : Fragment(), TextView.OnEditorActionListener {

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
        edt_login_pass.setOnEditorActionListener(this)

        btn_login.setOnClickListener {
            login()
        }
    }

    override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
        if (p1 == EditorInfo.IME_ACTION_DONE) {
            login()
        }
        return false
    }

    private fun login() {
        loginProgressBar.visibility = View.VISIBLE

        val prefs = SharedPreference(context)

        val accountNamel = edt_login_username.text.toString()
        val passwordl = edt_login_pass.text.toString()

        val request = NetworkUtils.getEndpoints()

        when {
            !Utils.checkInternetStatus(context!!) -> {
                loginProgressBar.visibility = View.GONE
                Toast.makeText(
                    context,
                    "Não foi possivel conectar-se à internet, por gentileza verifique sua conexão.",
                    Toast.LENGTH_LONG
                ).show()
            }
            accountNamel == "" || passwordl == "" -> {
                loginProgressBar.visibility = View.GONE
                Toast.makeText(
                    context,
                    "Username e Password não podem estar em branco.",
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {
                request.getLoginDetails(accountNamel, passwordl)
                    .enqueue(object : Callback<LoginDetails> {
                        override fun onFailure(call: Call<LoginDetails>, t: Throwable) {
                            loginProgressBar.visibility = View.GONE
                            Toast.makeText(
                                context,
                                "Ocorreu um erro ao processar o login, verifique as credenciais ou sua conexão com a internet.",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        override fun onResponse(
                            call: Call<LoginDetails>,
                            response: Response<LoginDetails>
                        ) {
                            val responseDetails = response.body()
                            loginProgressBar.visibility = View.GONE

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

}
