package com.example.services.jetpack_navigation.db

import androidx.room.*

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(userTable: UserItemTable)

    @Query("SELECT * FROM user_item_table")
    fun getAllItems(): List<UserItemTable>

    @Delete
    fun deleteItem(convertToUserTable: UserItemTable)
}