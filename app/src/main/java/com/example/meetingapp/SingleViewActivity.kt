package com.example.meetingapp

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetingapp.ViewModels.MeetingViewModel
import com.example.meetingapp.ViewModels.ViewModelFactory
import com.example.meetingapp.adapters.ExtraOptionsTextListAdapter
import com.example.meetingapp.database.MeetingDatabase
import com.example.meetingapp.repository.MeetingRepository
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.Format
import java.text.SimpleDateFormat
import java.util.Date

class SingleViewActivity : AppCompatActivity() {

    private val viewModel: MeetingViewModel by lazy{
        ViewModelProvider(
            this,
            ViewModelFactory(MeetingRepository(MeetingDatabase.invoke(this)))
        ).get(MeetingViewModel::class.java)
    }

    var id: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_single_view)

        id = intent.getLongExtra("meetingId", 0)

        val topAppBar: MaterialToolbar = findViewById(R.id.topAppBarMaterials)
        val name: TextView = findViewById(R.id.eventName)
        val description: TextView = findViewById(R.id.eventDescription)
        val startDate: TextView = findViewById(R.id.eventStart)
        val endDate: TextView = findViewById(R.id.eventEnd)
        val notifications: TextView = findViewById(R.id.eventNotifications)
        val extraOptions: TextView = findViewById(R.id.extraOptionsText)
        val recycler: RecyclerView = findViewById(R.id.extraOptionsRecycler)
        val adapter = ExtraOptionsTextListAdapter()

        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)

        topAppBar.setNavigationOnClickListener {
            finish()
        }

        topAppBar.setOnMenuItemClickListener {menuItem ->
            when (menuItem.itemId) {
                R.id.edit -> {
                    val intent = Intent(this, EventEditActivity::class.java).apply {
                        putExtra("EXTRA_ID", id)
                    }
                    startActivity(intent)
                    true
                }
                R.id.delete -> {

                    true
                }
                else -> false
            }
        }

        viewModel.getMeetingById(id).observe(this, Observer {meeting ->
            meeting?.let {

                val formatter: Format = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val start: String = formatter.format(it.meetings.meetingDatetime)
                val end: String = formatter.format(Date(it.meetings.meetingDatetime.time + it.meetings.meetingDuration))

                name.text = it.meetings.meetingName
                description.text = it.meetings.meetingDescription
                startDate.text = start
                endDate.text = end
                if (it.meetings.meetingNotification) {
                    notifications.text = "TAK"
                }
                else {
                    notifications.text = "NIE"
                }

                if (it.options.isEmpty()) {
                    extraOptions.text = ""
                }
                else {
                    adapter.submitList(it.options)
                }


                topAppBar.setOnMenuItemClickListener {menuItem ->
                    when (menuItem.itemId) {
                        R.id.edit -> {

                            true
                        }
                        R.id.delete -> {
                            MaterialAlertDialogBuilder(this)
                                .setTitle("Usuwanie wydarzenia")
                                .setMessage("Czy jesteś pewien, że chcesz usunąć to wydarzenie?")
                                .setNegativeButton("Nie") { dialog, which ->
                                    dialog.dismiss()
                                }
                                .setPositiveButton("Tak") { dialog, which ->
                                    viewModel.deleteCustomOptions(meeting.options)
                                    viewModel.deleteMeeting(meeting.meetings)
                                    dialog.dismiss()
                                    finish()
                                }
                                .show()
                            true
                        }
                        else -> false
                    }
                }
            }
        })
    }
}