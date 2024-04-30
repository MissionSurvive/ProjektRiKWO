package com.example.meetingapp

import androidx.room.Embedded
import androidx.room.Relation

data class MeetingsWithCustomOptions(
    @Embedded val meetings: Meetings,
    @Relation(
        parentColumn = "meetingId",
        entityColumn = "meetingId"
    )
    val options: List<CustomOptions>
)
