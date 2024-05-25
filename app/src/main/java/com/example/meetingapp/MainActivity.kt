package com.example.meetingapp

import android.os.Bundle
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetingapp.Adapters.DateListAdapter
import com.example.meetingapp.Items.DateItem
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var dateRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_month_view)

        calendarView = findViewById(R.id.calendarView)
        dateRecyclerView = findViewById(R.id.meeting_list)

        calendarView.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            val firstDayOfMonth = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
            val firstDate = "$firstDayOfMonth/${month + 1}/$year"

            // Get the last day of the month
            val lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            val lastDate = "$lastDayOfMonth/${month + 1}/$year"

            println("First day of the month: $firstDate")
            println("Last day of the month: $lastDate")
        })

        // Initialize lists
        val dateItems = listOf(
            DateItem(10, "May"),
            DateItem(11, "May"),
            DateItem(12, "May"),
            DateItem(13, "May"),
            DateItem(14, "May"),
        )

        // Initialize and set adapter for the dateRecyclerView
        val dateAdapter = DateListAdapter(dateItems)
        dateRecyclerView.adapter = dateAdapter
        dateRecyclerView.layoutManager = LinearLayoutManager(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}