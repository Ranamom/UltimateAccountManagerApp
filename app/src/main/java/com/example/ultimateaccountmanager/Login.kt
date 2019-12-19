package com.example.ultimateaccountmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ultimateaccountmanager.ui.login.LoginFragment

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }
    }

}
