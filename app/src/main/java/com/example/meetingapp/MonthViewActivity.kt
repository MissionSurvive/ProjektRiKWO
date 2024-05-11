package com.example.meetingapp

import android.os.Bundle
import android.widget.CalendarView
import android.widget.ListAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetingapp.Adapters.DateListAdapter
import com.example.meetingapp.Items.DateItem
import com.example.meetingapp.Items.MeetingItem


class MonthViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_month_view)
    }
}