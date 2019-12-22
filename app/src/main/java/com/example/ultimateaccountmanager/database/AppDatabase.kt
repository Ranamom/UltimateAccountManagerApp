package com.example.ultimateaccountmanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ultimateaccountmanager.models.Account
import com.example.ultimateaccountmanager.models.Character


@Database(entities = [Account::class, Character::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun Dao(): UltimateAccountManagerDao

    companion object {
        var INSTANCE: AppDatabase? = null

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE `character` ADD COLUMN `imageurl` TEXT NOT NULL DEFAULT 'https://outfits.ferobraglobal.com/animoutfit.php?id=128&addons=3&head=123&body=12&legs=23&feet=31&mount=0&direction=3'"
                )
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(context,AppDatabase::class.java,"database.db")
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE as AppDatabase
            } else {
                INSTANCE as AppDatabase
            }
        }

    }


}