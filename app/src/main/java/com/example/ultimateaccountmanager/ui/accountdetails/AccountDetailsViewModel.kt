package com.example.ultimateaccountmanager.ui.accountdetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.ultimateaccountmanager.repository.AppRepository

class AccountDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val appRepository = AppRepository(application)

    init {
//        appRepository.retriveAccountDataFromServer()
        appRepository.retriveCharacterDataFromServer()
    }

    fun getLiveAllCharacterData() = appRepository.getLiveAllCharacters()

    fun getLiveAccountData() = appRepository.getLiveAccountData()

    fun refreshData() {
//        appRepository.retriveAccountDataFromServer()
        appRepository.retriveCharacterDataFromServer()
    }
}
