package com.example.ultimateaccountmanager.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Character(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val level: Int,
    val vocation: String,
    val experience: Int,
    val imageurl: String,
    val imageurlanimated: String,
    val health: Int,
    val mana: Int,
    val healthmax: Int,
    val manamax: Int
)