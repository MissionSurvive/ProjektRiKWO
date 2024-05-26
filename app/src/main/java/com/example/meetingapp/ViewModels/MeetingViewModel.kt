package com.example.meetingapp.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.meetingapp.CustomOptions
import com.example.meetingapp.Meetings
import com.example.meetingapp.repository.MeetingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Date

class MeetingViewModel(private val meetingRepo : MeetingRepository) : ViewModel() {

    var returnedId: Long = 0L

    fun getAllMeetings() = meetingRepo.getAllMeetings()

    fun addMeeting(meeting: Meetings) =
        viewModelScope.async {
            returnedId = meetingRepo.insertMeeting(meeting)
        }

    fun addCustomOption(customOption: CustomOptions) =
        viewModelScope.launch {
            meetingRepo.insertCustomOption(customOption)
        }

    fun deleteMeeting(meeting: Meetings) =
        viewModelScope.launch {
            meetingRepo.deleteMeeting(meeting)
        }

    fun updateMeeting(meeting: Meetings) =
        viewModelScope.launch {
            meetingRepo.updateMeeting(meeting)
        }

    fun deleteCustomOptions(customOptions: List<CustomOptions>) = viewModelScope.launch {
        meetingRepo.deleteCustomOptions(customOptions)
    }

    fun getMeetingById(id: Long) = meetingRepo.getMeeingById(id).asLiveData()

    fun getMeetingsBetweenDates(startDate: Date, endDate: Date) = meetingRepo.getMeetingsBetweenDates(startDate, endDate).asFlow()
}