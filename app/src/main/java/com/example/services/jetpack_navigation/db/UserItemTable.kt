package com.example.services.jetpack_navigation.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(
    tableName = "user_item_table",
    foreignKeys = [ForeignKey(
        entity = UserTable::class,
        parentColumns = ["userId"],
        childColumns = ["userIdFK"],
        onDelete = ForeignKey.CASCADE
    )],
)
data class UserItemTable(
    @PrimaryKey(autoGenerate = true)
    var itemId: Int = 0,
    var userIdFK: String,
    val type: String?)