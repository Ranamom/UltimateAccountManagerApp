package com.example.ultimateaccountmanager.ui.accountdetails

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.ultimateaccountmanager.R
import com.example.ultimateaccountmanager.commons.CharacterAdapter
import com.example.ultimateaccountmanager.models.Account
import com.example.ultimateaccountmanager.util.Utils
import kotlinx.android.synthetic.main.account_details_fragment.*
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
        setHasOptionsMenu(true)
        val actionBar = (activity as AppCompatActivity?)!!.supportActionBar
        actionBar?.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logoutmenu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logoutButton) {
            val builder = context?.let { AlertDialog.Builder(it) }
            builder!!.setMessage("You realy want to logoff?")
            builder.setPositiveButton("yes") { dialog, which ->
                Utils.clearAllData(context!!, "Logoff success!!")
            }
            builder.setNegativeButton("no") { dialog, which ->
                //do Nothing
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Utils.hideSoftKeyBoard(context!!, view!!)

        viewModel = ViewModelProviders.of(this).get(AccountDetailsViewModel::class.java)

        Timber.plant(Timber.DebugTree())
        val observeAccount =
            Observer<Account> { account ->

                if (account != null) {

                    val premdays = if (account.premdays > 0) {
                        "Premium Account"
                    } else {
                        "Free Account"
                    }
                    txt_account_details_status.text = premdays
                    txt_account_details_name.text = account.name.capitalize()
                    Glide.with(view!!.context).load(R.drawable.animoutfit2)
                        .into(img_account_details_profile)
                    accountDetailsProgressBar.visibility = View.GONE
                }

            }
        viewModel.getLiveAccountData().observe(viewLifecycleOwner, observeAccount)
        viewModel.getLiveAllCharacterData().observe(viewLifecycleOwner, Observer { characters ->
            rcv_account_details_character.adapter = CharacterAdapter(characters, context!!)
        })
    }
}
