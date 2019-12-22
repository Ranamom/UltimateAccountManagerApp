package com.example.ultimateaccountmanager.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Character (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val level: Int,
    val vocation: Int,
    val experience: Int,
    val imageurl: String
)