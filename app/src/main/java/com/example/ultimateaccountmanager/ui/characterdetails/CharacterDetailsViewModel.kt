package com.example.ultimateaccountmanager.ui.characterdetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.ultimateaccountmanager.repository.AppRepository

class CharacterDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val appRepository = AppRepository(application)

    init {
        appRepository.retriveCharacterDataFromServer()
    }

    fun setCharacterCurrentId(id: Int) {
        appRepository.setCharacterCurrentId(id)
    }

    fun getLiveCharacterData() =
        appRepository.getLiveSingleCharacterDetails(getCharacterCurrentId().value!!)

    fun getCharacterCurrentId() = appRepository.characterCurrentId
}
