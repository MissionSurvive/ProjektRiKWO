package com.example.meetingapp.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.meetingapp.repository.MeetingRepository

class ViewModelFactory(private val repository: MeetingRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MeetingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MeetingViewModel(repository) as T
        }
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}