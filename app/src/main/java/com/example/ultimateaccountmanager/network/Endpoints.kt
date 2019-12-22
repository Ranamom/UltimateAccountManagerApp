package com.example.ultimateaccountmanager.network

import com.example.ultimateaccountmanager.models.Account
import com.example.ultimateaccountmanager.models.Character
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

    @GET("account")
    fun getAccountDetails(
        @Query("key") key: String
    ): Call<Account>

    @GET("characters")
    fun getCharactersDetails(
        @Query("key") key: String
    ): Call<List<Character>>
}