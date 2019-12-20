package com.example.ultimateaccountmanager.models


data class LoginDetails(
    val status: String,
    val type: String,
    val msg: List<String>
)