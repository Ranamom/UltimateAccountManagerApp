package com.example.ultimateaccountmanager.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.ultimateaccountmanager.R
import com.example.ultimateaccountmanager.models.LoginDetails
import com.example.ultimateaccountmanager.network.NetworkUtils
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
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()

        txt_login_sign_up.setOnClickListener {
            Toast.makeText(context, "Eu ainda não fui implementado ):", Toast.LENGTH_SHORT).show()
        }

        btn_login.setOnClickListener {

            val accountNamel = edt_login_username.text.toString()
            val passwordl = edt_login_pass.text.toString()


            val request = NetworkUtils.getEndpoints()

            request.getLoginDetails(accountNamel, passwordl)
                .enqueue(object : Callback<LoginDetails> {
                    override fun onFailure(call: Call<LoginDetails>, t: Throwable) {
                        Timber.d("Deu bosta ai, ${t}")
                    }

                    override fun onResponse(
                        call: Call<LoginDetails>,
                        response: Response<LoginDetails>
                    ) {
                        val responseDetails = response.body()

                        Timber.d(responseDetails.toString())

                        if (responseDetails!!.status.equals("error")) {
                            responseDetails.msg.forEach {
                                Toast.makeText(
                                    context,
                                    "-> ${responseDetails.status} \n -> ${responseDetails.type} \n -> ${it}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else if (responseDetails!!.status.equals("success")) {
                            responseDetails.msg.forEach {
                                Toast.makeText(
                                    context,
                                    "-> ${responseDetails.status} \n -> ${responseDetails.type} \n -> ${it}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                })

        }

//
//        Handler().postDelayed({
//            findNavController().navigate(R.id.action_loginFragment_to_listCharactersFragment)
//        }, 2000)
    }

}
