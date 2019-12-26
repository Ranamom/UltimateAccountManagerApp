package com.example.ultimateaccountmanager.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.ultimateaccountmanager.models.Account
import com.example.ultimateaccountmanager.models.Character

@Dao
interface UltimateAccountManagerDao {

    @Query("select * from character")
    fun getAllCharacterData(): List<Character>

    @Query("select * from character")
    fun getLiveAllCharacterData(): LiveData<List<Character>>

    @Query("select * from accounts")
    fun getAccountData(): Account

    @Query("select * from accounts")
    fun getLiveAccountData(): LiveData<Account>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun singleAccountInsert(account: Account)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun singleCharacterInsert(character: Character)

    @Query("delete from character")
    fun deleteAllCharacterData()

    @Query("delete from accounts")
    fun deleteAllAccountData()

    @Query("select * from character where id = :id")
    fun getLiveSingleCharacterDetails(id: Int): LiveData<Character>
}