package com.example.meetingapp.repository

import com.example.meetingapp.CustomOptions
import com.example.meetingapp.Meetings
import com.example.meetingapp.TemplateCustomOptions
import com.example.meetingapp.TemplateWithTemplateOptions
import com.example.meetingapp.Templates
import com.example.meetingapp.database.MeetingDatabase

class MeetingRepository(private val db: MeetingDatabase) {
    suspend fun insertMeeting(meetings: Meetings) = db.getMeetingDoa().insertMeeting(meetings)
    suspend fun insertCustomOption(customOptions: CustomOptions) = db.getMeetingDoa().insertCustomOption(customOptions)
    suspend fun insertTemplate(templates: Templates) = db.getTemplateDao().insertTemplate(templates)
    suspend fun insertTemplateOption(templateOptions: TemplateCustomOptions) = db.getTemplateDao().insertTemplateOption(templateOptions)

    suspend fun updateMeeting(meetings: Meetings) = db.getMeetingDoa().updateMeeting(meetings)
    suspend fun updateCustomOptions(customOptions: CustomOptions) = db.getMeetingDoa().updateCustomOption(customOptions)
    suspend fun updateTemplate(templates: Templates) = db.getTemplateDao().updateTemplate(templates)
    suspend fun updateTemplateOptions(templateOptions: TemplateCustomOptions) = db.getTemplateDao().updateTemplateOption(templateOptions)

    suspend fun deleteMeeting(meetings: Meetings) = db.getMeetingDoa().deleteMeeing(meetings)
    suspend fun deleteCustomOption(customOptions: CustomOptions) = db.getMeetingDoa().deleteCustomOption(customOptions)
    suspend fun deleteTemplate(templates: Templates) = db.getTemplateDao().deleteTemplate(templates)
    suspend fun deleteTemplateOption(templateOptions: TemplateCustomOptions) = db.getTemplateDao().deleteTemplateOption(templateOptions)

    fun getAllMeetings() = db.getMeetingDoa().getAllMeetings()
    fun getAllCustomOptions() = db.getMeetingDoa().getAllCustomOptions()
    fun getAllTemplates() = db.getTemplateDao().getAllTemplates()
    fun getAllTemplatesOptions() = db.getTemplateDao().getAllTemplateOptions()
}