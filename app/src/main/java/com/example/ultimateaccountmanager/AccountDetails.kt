package com.example.ultimateaccountmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ultimateaccountmanager.ui.accountdetails.AccountDetailsFragment

class AccountDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_details_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AccountDetailsFragment.newInstance())
                .commitNow()
        }
    }

}
