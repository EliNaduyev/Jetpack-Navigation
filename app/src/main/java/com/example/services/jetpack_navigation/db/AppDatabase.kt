package com.example.services.jetpack_navigation.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserTable::class, UserItemTable::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getUserItemDao(): ItemDao
}