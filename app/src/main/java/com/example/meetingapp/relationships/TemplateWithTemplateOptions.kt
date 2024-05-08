package com.example.meetingapp

import androidx.room.Embedded
import androidx.room.Relation

data class TemplateWithTemplateOptions(
    @Embedded val templates: Templates,
    @Relation(
        parentColumn = "templateId",
        entityColumn = "templateId"
    )
    val options: List<TemplateCustomOptions>
)
