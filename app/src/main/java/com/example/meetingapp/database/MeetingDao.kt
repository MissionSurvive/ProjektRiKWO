package com.example.meetingapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.meetingapp.CustomOptions
import com.example.meetingapp.Meetings
import com.example.meetingapp.MeetingsWithCustomOptions

@Dao
interface MeetingDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMeeting(meetings: Meetings)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCustomOption(customOptions: CustomOptions)

    @Update
    suspend fun updateMeeting(meetings: Meetings)
    @Update
    suspend fun updateCustomOption(customOptions: CustomOptions)

    @Delete
    suspend fun deleteMeeting(meetings: Meetings)

    @Delete
    suspend fun deleteCustomOption(customOptions: CustomOptions)

    @Query("SELECT * FROM MEETINGS ORDER BY meetingDatetime DESC")
    fun getAllMeetings(): LiveData<List<MeetingsWithCustomOptions>>

    @Query("SELECT * FROM CUSTOMOPTIONS")
    fun getAllCustomOptions(): LiveData<List<CustomOptions>>
}
