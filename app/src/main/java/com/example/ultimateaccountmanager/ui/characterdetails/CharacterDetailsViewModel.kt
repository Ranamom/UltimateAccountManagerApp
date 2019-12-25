package com.example.ultimateaccountmanager.ui.characterdetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.ultimateaccountmanager.repository.AppRepository

class CharacterDetailsViewModel(application: Application) : AndroidViewModel(application) {
    val appRepository = AppRepository(application)
}
