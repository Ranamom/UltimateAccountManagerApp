package com.example.ultimateaccountmanager.ui.accountdetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ultimateaccountmanager.R

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
        // TODO: Use the ViewModel
    }

}
