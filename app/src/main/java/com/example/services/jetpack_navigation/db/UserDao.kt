package com.example.services.jetpack_navigation.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(userTable: UserTable)

    @Query("SELECT * FROM user_table")
    fun getAllUsersObservable(): LiveData<List<UserTable>>

//    @Query("SELECT * FROM settings WHERE userId = :id")
//    fun getRecord(id: String): SettingsTableDto
//
//    @Delete
//    suspend fun deleteRecord(settingsDto: SettingsTableDto)
//
//    @Update
//    suspend fun updateRecord(settingsDto: SettingsTableDto)
}