package com.example.meetingapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "templates")
data class Templates(
    @PrimaryKey(autoGenerate = true) val templateId: Int,
    val templateName: String,
    val templateMeetingName: String,
    val templateDescription: String
)