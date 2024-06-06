package com.example.meetingapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "templateoptions")
data class TemplateCustomOptions(
    @PrimaryKey(autoGenerate = true) val templateOptionId: Int,
    val templateOptionKey: String,
    val templateOptionValue: String,
    val templateId: Int
)
