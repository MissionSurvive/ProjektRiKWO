package com.example.meetingapp

import android.app.LauncherActivity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.meetingapp.ViewModels.MeetingViewModel
import com.example.meetingapp.ViewModels.ViewModelFactory
import com.example.meetingapp.database.MeetingDatabase
import com.example.meetingapp.repository.MeetingRepository

class MainActivity : AppCompatActivity() {

        private val viewModel: MeetingViewModel by lazy{
            ViewModelProvider(
                this,
                ViewModelFactory(MeetingRepository(MeetingDatabase.invoke(this)))
            ).get(MeetingViewModel::class.java)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val intent = Intent(this, SingleViewActivity::class.java)
        intent.putExtra("meetingId", 1L)
        startActivity(intent)
        val intentTwo = Intent(this, EventAddActivity::class.java)
        startActivity(intentTwo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
