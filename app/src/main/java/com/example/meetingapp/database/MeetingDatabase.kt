package com.example.meetingapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.meetingapp.Converters.Converters
import com.example.meetingapp.CustomOptions
import com.example.meetingapp.Meetings
import com.example.meetingapp.TemplateCustomOptions
import com.example.meetingapp.Templates

@Database(entities = [Meetings::class, CustomOptions::class, Templates::class, TemplateCustomOptions::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MeetingDatabase : RoomDatabase(){

    abstract fun getMeetingDoa(): MeetingDao
    abstract fun getTemplateDao(): TemplateDao

    companion object{
        @Volatile
        private var instance: MeetingDatabase? = null
        private val LOCK = Any()

        open fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?:
            createDatabase(context).also{
                instance= it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MeetingDatabase::class.java,
                "meeting_db"
            ).build()
    }
}