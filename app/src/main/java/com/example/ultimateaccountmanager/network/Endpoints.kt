package com.example.ultimateaccountmanager.network

import com.example.ultimateaccountmanager.models.LoginCredentials
import com.example.ultimateaccountmanager.models.LoginDetails
import retrofit2.Call
import retrofit2.http.*

interface Endpoints {

    @FormUrlEncoded
    @POST("login")
    fun getLoginDetails(
        @Field("accountNamel") accountNamel: String,
        @Field("passwordl") passwordl: String
    ): Call<LoginDetails>
}