package com.example.meetingapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetingapp.Adapters.DateListAdapter
import com.example.meetingapp.Items.DateItem
import com.example.meetingapp.Items.MeetingItem

class MainActivity : AppCompatActivity() {
    private lateinit var dateRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_month_view)

        dateRecyclerView = findViewById(R.id.meeting_list)

        val dateItems = listOf(
            DateItem("May"),
            DateItem("June"),
            DateItem("July"),
            DateItem("August"),
            DateItem("September"),
        )

        val meetings = listOf(
            DateItem("May") to listOf(
                MeetingItem(8, 5, 2024, "Strategy Meeting", 10, 0, 12, 30),
                MeetingItem(22, 5, 2024, "Stakeholder Update", 14, 0, 15, 30)
            ),
            DateItem("June") to listOf(
                MeetingItem(5, 6, 2024, "Technical Workshop", 9, 0, 11, 30),
                MeetingItem(19, 6, 2024, "Team Building", 13, 30, 16, 0)
            ),
            DateItem("July") to listOf(
                MeetingItem(3, 7, 2024, "Project Review", 10, 30, 12, 30),
                MeetingItem(17, 7, 2024, "Client Meeting", 14, 0, 16, 0)
            ),
            DateItem("August") to listOf(
                MeetingItem(7, 8, 2024, "Roadmap Planning", 9, 30, 11, 30),
                MeetingItem(21, 8, 2024, "Process Improvement", 13, 0, 15, 0)
            ),
            DateItem("September") to listOf(
                MeetingItem(4, 9, 2024, "Product Demo", 10, 0, 12, 0),
                MeetingItem(18, 9, 2024, "Cross-Team Sync", 14, 30, 16, 0)
            ),
        )

        val dateAdapter = DateListAdapter(dateItems, meetings)
        dateRecyclerView.adapter = dateAdapter
        dateRecyclerView.layoutManager = LinearLayoutManager(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}