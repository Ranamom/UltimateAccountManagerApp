package com.example.ultimateaccountmanager.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val type: Int,
    val premdays: Int,
    val coins: Int,
    val email: String,
    val key: String,
    val rlname: String
)