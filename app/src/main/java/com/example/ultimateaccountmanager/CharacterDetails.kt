package com.example.ultimateaccountmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ultimateaccountmanager.ui.characterdetails.CharacterDetailsFragment

class CharacterDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_details_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CharacterDetailsFragment.newInstance())
                .commitNow()
        }
    }

}
