package com.example.ultimateaccountmanager.ui.accountdetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ultimateaccountmanager.repository.AppRepository

class AccountDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val appRepository = AppRepository(application)

    init {
        appRepository.retriveAccountDataFromServer()
        appRepository.retriveCharacterDataFromServer()
    }

    val test : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun getLiveAllCharacterData() = appRepository.getLiveAllCharacters()
    fun getLiveAccountData() = appRepository.getLiveAccountData()
    fun getCurrentCharacterId() = appRepository.characterCurrentId
}
