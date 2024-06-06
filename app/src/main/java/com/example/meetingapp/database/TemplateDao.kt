package com.example.meetingapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.meetingapp.TemplateCustomOptions
import com.example.meetingapp.TemplateWithTemplateOptions
import com.example.meetingapp.Templates
@Dao
interface TemplateDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTemplate(templates: Templates)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTemplateOption(templateCustomOptions: TemplateCustomOptions)

    @Update
    suspend fun updateTemplate(templates: Templates)
    @Update
    suspend fun updateTemplateOption(templateCustomOptions: TemplateCustomOptions)

    @Delete
    suspend fun deleteTemplate(templates: Templates)

    @Delete
    suspend fun deleteTemplateOption(templateCustomOptions: TemplateCustomOptions)

    @Query("SELECT * FROM TEMPLATES ORDER BY templateId DESC")
    fun getAllTemplates(): LiveData<List<TemplateWithTemplateOptions>>

    @Query("SELECT * FROM TEMPLATEOPTIONS")
    fun getAllTemplateOptions(): LiveData<List<TemplateCustomOptions>>
}