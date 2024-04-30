package com.example.meetingapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "templates")
data class Templates(
    @PrimaryKey(autoGenerate = true) val templateId: Int,
    @ColumnInfo(name = "meeting_name") val templateName: String,
    @ColumnInfo(name = "meeting_name") val templateMeetingName: String,
    @ColumnInfo(name = "meeting_description") val templateDescription: String
)