package com.example.ultimateaccountmanager.network

import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkUtils {

    fun getRetrofitInstance(url: String): Retrofit {

        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getEndpoints() =
        getRetrofitInstance("https://uam.codenome.com/ajax/").create(Endpoints::class.java)
}

