package com.example.meetingapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "customoptions")
data class CustomOptions(
    @PrimaryKey(autoGenerate = true) val optionId: Int,
    @ColumnInfo(name = "customoption_key") val optionKey: String,
    @ColumnInfo(name = "customoption_value") val optionValue: String,
    @ColumnInfo(name = "fk_meeting_id") val meetingId: Int
)
