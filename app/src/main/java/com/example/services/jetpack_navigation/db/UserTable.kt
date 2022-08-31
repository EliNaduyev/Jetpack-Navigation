package com.example.services.jetpack_navigation.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


/**
 * PrimaryKey - Can't be null
 *
 * in the DAO interface i can decide how to act if  new record created with the same
 * PrimaryKey Ignore, Replace etc..
 */
@Entity(tableName = "user_table")

data class UserTable(
    @PrimaryKey var userId: String,
    val name: String? = null)