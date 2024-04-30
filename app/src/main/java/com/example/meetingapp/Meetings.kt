package com.example.meetingapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "meetings")
data class Meetings(
    @PrimaryKey(autoGenerate = true) val meetingId: Int,
    @ColumnInfo(name = "meeting_name") val meetingName: String,
    @ColumnInfo(name = "meeting_description") val meetingDescription: String,
    @ColumnInfo(name = "meeting_datetime") val meetingDatetime: Date,
    @ColumnInfo(name = "meeting_duration") val meetingDuration: Int,
    @ColumnInfo(name = "meeting_notification") val meetingNotification: Boolean
)