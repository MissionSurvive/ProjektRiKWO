package com.example.meetingapp

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetingapp.Adapters.DateListAdapter
import com.example.meetingapp.Adapters.MeetingListAdapter
import com.example.meetingapp.Converters.Converters
import com.example.meetingapp.Items.DateItem
import com.example.meetingapp.Items.MeetingItem
import com.example.meetingapp.ViewModels.MeetingViewModel
import com.example.meetingapp.ViewModels.ViewModelFactory
import com.example.meetingapp.database.MeetingDatabase
import com.example.meetingapp.repository.MeetingRepository
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var dateRecyclerView: RecyclerView
    private lateinit var meetingRepository: MeetingRepository
    private lateinit var meetingDatabase: MeetingDatabase
    private lateinit var meetingListAdapter: MeetingListAdapter
    val converters = Converters()

    private val viewModel: MeetingViewModel by lazy{
        ViewModelProvider(
            this,
            ViewModelFactory(MeetingRepository(MeetingDatabase.invoke(this)))
        ).get(MeetingViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_month_view)

        val topAppBar: MaterialToolbar = findViewById(R.id.topAppBar)
        calendarView = findViewById(R.id.calendarView)
        dateRecyclerView = findViewById(R.id.meeting_list)
        meetingDatabase = MeetingDatabase.invoke(this)
        meetingRepository = MeetingRepository(meetingDatabase)

        meetingListAdapter = MeetingListAdapter(emptyList(), this)

        calendarView.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)



            val startDate = calendar.timeInMillis
            val endCalendar = Calendar.getInstance()
            endCalendar.set(year, month, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            val endDate = endCalendar.timeInMillis
            println(startDate)
            println(endDate)

            observeMeetingsBetweenDates(startDate, endDate)
        })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        topAppBar.setOnMenuItemClickListener {menuItem ->
            when (menuItem.itemId) {
                R.id.add -> {
                    val intent = Intent(this, EventAddActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun observeMeetingsBetweenDates(startDate: Long, endDate: Long) {
        meetingRepository.getMeetingsBetweenDates(Date(startDate), Date(endDate)).observe(this) { meetings ->
            // Update the adapters with the retrieved meetings
            updateDateAdapter(meetings)
            //updateMeetingAdapter(meetings)
        }
    }
    private fun updateDateAdapter(meetings: List<MeetingsWithCustomOptions>) {
        // Create a list of DateItem objects from the meetings
        val dateItems = meetings.map { meeting ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = converters.fromDateToLong(meeting.meetings.meetingDatetime)!!
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
            DateItem(day, month)
        }.distinct()

        // Initialize and set the adapter for the dateRecyclerView
        val dateAdapter = DateListAdapter(dateItems, meetings, this)
        dateRecyclerView.adapter = dateAdapter
        dateRecyclerView.layoutManager = LinearLayoutManager(this)
    }
    /*private fun updateMeetingAdapter(meetings: List<MeetingsWithCustomOptions>) {
        // Group meetings by date
        val meetingsByDate = meetings.groupBy { meeting ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = converters.fromDateToLong(meeting.meetings.meetingDatetime)!!
            calendar.get(Calendar.DAY_OF_MONTH)
        }

        // Update the MeetingListAdapter for each DateViewHolder
        dateRecyclerView.adapter?.let { adapter ->
            for (i in 0 until adapter.itemCount) {
                val holder = dateRecyclerView.findViewHolderForAdapterPosition(i) as? DateListAdapter.DateViewHolder
                holder?.let { dateViewHolder ->
                    val day = dateViewHolder.textView.text.toString().split(" ").first().toInt()
                    val meetingsForDay = meetingsByDate[day]?.map { MeetingItem(it.meetings.meetingName) } ?: emptyList()
                    val meetingAdapter = MeetingListAdapter(meetingsForDay)
                    dateViewHolder.meetingRecyclerView.adapter = meetingAdapter
                }
            }
        }
    }*/
}