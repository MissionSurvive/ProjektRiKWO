package com.example.meetingapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "templateoptions")
data class TemplateCustomOptions(
    @PrimaryKey(autoGenerate = true) val templateOptionId: Int,
    @ColumnInfo(name = "templateoption_key") val templateOptionKey: String,
    @ColumnInfo(name = "templateoption_value") val templateOptionValue: String,
    @ColumnInfo(name = "fk_template_id") val templateId: Int
)
