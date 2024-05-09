package com.example.meetingapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "meetings")
data class Meetings(
    @PrimaryKey(autoGenerate = true) val meetingId: Int,
    val meetingName: String,
    val meetingDescription: String,
    val meetingDatetime: Date,
    val meetingDuration: Int,
    val meetingNotification: Boolean
)