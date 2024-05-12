package com.example.meetingapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "customoptions")
data class CustomOptions(
    @PrimaryKey(autoGenerate = true) val optionId: Int,
    val optionKey: String,
    var optionValue: String,
    val meetingId: Int
)
