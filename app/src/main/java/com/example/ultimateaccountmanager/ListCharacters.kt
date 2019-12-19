package com.example.ultimateaccountmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ultimateaccountmanager.ui.listcharacters.ListCharactersFragment

class ListCharacters : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_characters_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ListCharactersFragment.newInstance())
                .commitNow()
        }
    }

}
