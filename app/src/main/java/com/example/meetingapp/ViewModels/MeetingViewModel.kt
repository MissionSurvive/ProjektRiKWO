package com.example.meetingapp.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.meetingapp.Meetings
import com.example.meetingapp.repository.MeetingRepository
import kotlinx.coroutines.launch

class MeetingViewModel(private val meetingRepo : MeetingRepository) : ViewModel() {
    fun getAllMeetings() = meetingRepo.getAllMeetings()

    fun addMeeting(meeting: Meetings) =
        viewModelScope.launch {
            meetingRepo.insertMeeting(meeting)
        }

    fun deleteMeeting(meeting: Meetings) =
        viewModelScope.launch {
            meetingRepo.deleteMeeting(meeting)
        }

    fun updateMeeting(meeting: Meetings) =
        viewModelScope.launch {
            meetingRepo.updateMeeting(meeting)
        }
}