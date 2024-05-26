package com.example.meetingapp

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetingapp.ViewModels.MeetingViewModel
import com.example.meetingapp.ViewModels.ViewModelFactory
import com.example.meetingapp.adapters.ExtraOptionsListAdapter
import com.example.meetingapp.database.MeetingDatabase
import com.example.meetingapp.repository.MeetingRepository
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CalendarConstraints.DateValidator
import com.google.android.material.datepicker.CompositeDateValidator
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.launch
import java.text.Format
import java.text.SimpleDateFormat
import java.util.Date

class EventEditActivity : AppCompatActivity() {
    private val viewModel: MeetingViewModel by lazy{
        ViewModelProvider(
            this,
            ViewModelFactory(MeetingRepository(MeetingDatabase.invoke(this)))
        ).get(MeetingViewModel::class.java)
    }

    var startDateSelection: Long = 0
    var endDateSelection: Long = 0
    var extraOptions: MutableList<CustomOptions> = mutableListOf()
    var startUnixTime: Long = 0
    var endUnixTime: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_event_edit)

        var id = intent.getLongExtra("EXTRA_ID", 0L)
        val topAppBar: MaterialToolbar = findViewById(R.id.topAppBarMaterials)
        val nameInput: TextInputEditText = findViewById(R.id.nameInputEditText)
        val descriptionInput: TextInputEditText = findViewById(R.id.descriptionInputEditText)
        val startDateInput: TextInputEditText = findViewById(R.id.startInputEditText)
        val endDateInput: TextInputEditText = findViewById(R.id.endInputEditText)
        val checkBox: CheckBox = findViewById(R.id.checkbox)
        val extraOptionsRecyclerView: RecyclerView = findViewById(R.id.extraOptionsRecyclerView)
        val extendedFAB: ExtendedFloatingActionButton = findViewById(R.id.extendedFloatingActionButton)

        val extraOptionsAdapter = ExtraOptionsListAdapter(extraOptions)
        extraOptionsRecyclerView.adapter = extraOptionsAdapter
        extraOptionsRecyclerView.layoutManager = LinearLayoutManager(this)

        startDateInput.setRawInputType(InputType.TYPE_NULL)
        endDateInput.setRawInputType(InputType.TYPE_NULL)

        viewModel.getMeetingById(id).observe(this, Observer {meeting ->
            meeting?.let {

                val formatter: Format = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val start: String = formatter.format(it.meetings.meetingDatetime)
                val end: String = formatter.format(Date(it.meetings.meetingDatetime.time + it.meetings.meetingDuration))

                nameInput.setText(it.meetings.meetingName)
                descriptionInput.setText(it.meetings.meetingDescription)
                startDateInput.setText(start)
                endDateInput.setText(end)
                checkBox.isChecked = it.meetings.meetingNotification
                startUnixTime = it.meetings.meetingDatetime.time
                endUnixTime = startUnixTime + it.meetings.meetingDuration
                if (it.options.isEmpty()) {

                }
                else {
                    for((index, key, value) in it.options){
                        extraOptions.add(CustomOptions(index, key, value, 0))
                        extraOptionsAdapter.submitList(extraOptions)
                        extraOptionsAdapter.notifyItemInserted(extraOptions.size - 1)
                    }
                }
            }
        })
        //start date event

        startDateInput.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val constraintsBuilder: CalendarConstraints.Builder
                if (endUnixTime == 0L)  {
                    constraintsBuilder =
                        CalendarConstraints.Builder()
                            .setValidator(DateValidatorPointForward.now())
                }
                else {
                    val listValidators: ArrayList<DateValidator> = ArrayList()
                    listValidators.apply {
                        add(DateValidatorPointForward.now())
                        add(DateValidatorPointBackward.before(endDateSelection))
                    }
                    val validators = CompositeDateValidator.allOf(listValidators)
                    constraintsBuilder =
                        CalendarConstraints.Builder()
                            .setValidator(validators)
                }
                val datePicker =
                    MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Wybierz datę")
                        .setCalendarConstraints(constraintsBuilder.build())
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .setNegativeButtonText("Anuluj")
                        .setPositiveButtonText("Dalej")
                        .build()

                datePicker.show(supportFragmentManager, "datePicker")

                datePicker.addOnNegativeButtonClickListener {
                    datePicker.dismiss()
                    v.clearFocus()
                }

                datePicker.addOnPositiveButtonClickListener {
                    val timePicker =
                        MaterialTimePicker.Builder()
                            .setTimeFormat(TimeFormat.CLOCK_24H)
                            .setTitleText("Wybierz czas")
                            .setNegativeButtonText("Anuluj")
                            .setPositiveButtonText("Potwierdź")
                            .build()

                    timePicker.show(supportFragmentManager, "timePicker")

                    timePicker.addOnNegativeButtonClickListener {
                        datePicker.dismiss()
                        timePicker.dismiss()
                        v.clearFocus()
                    }

                    timePicker.addOnPositiveButtonClickListener {
                        startDateSelection = datePicker.selection!!
                        var minuteSelection: Long = timePicker.minute.toLong()
                        var hourSelection: Long = timePicker.hour.toLong()

                        minuteSelection *= 60000
                        hourSelection *= 3600000

                        startUnixTime = startDateSelection + hourSelection + minuteSelection

                        val formatter: Format = SimpleDateFormat("dd/MM/yyyy HH:mm")
                        val s: String = formatter.format(Date(startUnixTime))
                        startDateInput.setText(s)
                        datePicker.dismiss()
                        timePicker.dismiss()
                        v.clearFocus()
                    }
                }
            }
        }

        //end date events

        endDateInput.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                val constraintsBuilder: CalendarConstraints.Builder
                if (startUnixTime == 0L) {
                    constraintsBuilder =
                        CalendarConstraints.Builder()
                            .setValidator(DateValidatorPointForward.now())
                }
                else {
                    constraintsBuilder =
                        CalendarConstraints.Builder()
                            .setValidator(DateValidatorPointForward.from(startDateSelection))
                }
                val datePicker =
                    MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Wybierz datę")
                        .setCalendarConstraints(constraintsBuilder.build())
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .setNegativeButtonText("Anuluj")
                        .setPositiveButtonText("Dalej")
                        .build()

                datePicker.show(supportFragmentManager, "datePicker")

                datePicker.addOnNegativeButtonClickListener {
                    datePicker.dismiss()
                    v.clearFocus()
                }

                datePicker.addOnPositiveButtonClickListener {
                    val timePicker =
                        MaterialTimePicker.Builder()
                            .setTimeFormat(TimeFormat.CLOCK_24H)
                            .setTitleText("Wybierz czas")
                            .build()

                    timePicker.show(supportFragmentManager, "timePicker")

                    timePicker.addOnNegativeButtonClickListener {
                        datePicker.dismiss()
                        timePicker.dismiss()
                        v.clearFocus()
                    }

                    timePicker.addOnPositiveButtonClickListener {
                        endDateSelection = datePicker.selection!!
                        var minuteSelection: Long = timePicker.minute.toLong()
                        var hourSelection: Long = timePicker.hour.toLong()

                        minuteSelection *= 60000
                        hourSelection *= 3600000

                        endUnixTime = endDateSelection + hourSelection + minuteSelection

                        val formatter: Format = SimpleDateFormat("dd/MM/yyyy HH:mm")
                        val s: String = formatter.format(Date(endUnixTime))
                        endDateInput.setText(s)
                        datePicker.dismiss()
                        timePicker.dismiss()
                        v.clearFocus()
                    }
                }
            }
        }

        extendedFAB.setOnClickListener {

            val dialogInside = LayoutInflater.from(this)
                .inflate(R.layout.event_add_dialog, null, false)
            val dialog = MaterialAlertDialogBuilder(this)
                .setTitle("Dodawanie niestandardowego pola")
                .setNegativeButton("Anuluj") { dialog, which ->
                    dialog.dismiss()
                }
                .setPositiveButton("Dodaj") { dialog, which ->
                    val optionNameTextField: TextInputEditText = dialogInside.findViewById(R.id.nameInputEditText)
                    val optionContentTextField: TextInputEditText = dialogInside.findViewById(R.id.contentInputEditText)

                    if (!optionContentTextField.text.isNullOrEmpty() && !optionNameTextField.text.isNullOrEmpty()) {
                        extraOptions.add(CustomOptions(0, optionNameTextField.text.toString(), optionContentTextField.text.toString(), 0))
                        extraOptionsAdapter.submitList(extraOptions)
                        extraOptionsAdapter.notifyItemInserted(extraOptions.size - 1)
                        dialog.dismiss()
                    }
                    else {
                        Toast.makeText(this, "Wszystkie pola muszą być wypełnione.", Toast.LENGTH_SHORT).show()
                    }
                }
            dialog.setView(dialogInside)
            dialog.show()
        }

        topAppBar.setOnMenuItemClickListener {menuItem ->
            when (menuItem.itemId) {
                R.id.done -> {
                    if (nameInput.text.isNullOrEmpty() || startUnixTime == 0L || endUnixTime == 0L || extraOptionsAdapter.isTextEmpty) {
                        Toast.makeText(this, "Wszystkie pola poza opisem wydarzenia muszą być wypełnione.", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        var meeting: Meetings
                        if (descriptionInput.text.isNullOrEmpty()) {
                            meeting = Meetings(
                                0,
                                nameInput.text.toString(),
                                "null",
                                Date(startUnixTime),
                                (endUnixTime - startUnixTime).toInt(),
                                checkBox.isChecked
                            )
                        } else {
                            meeting = Meetings(
                                0,
                                nameInput.text.toString(),
                                descriptionInput.text.toString(),
                                Date(startUnixTime),
                                (endUnixTime - startUnixTime).toInt(),
                                checkBox.isChecked
                            )
                        }

                        viewModel.getMeetingById(id).observe(this, Observer { meeting ->
                            meeting?.let {
                                viewModel.deleteCustomOptions(meeting.options)
                                viewModel.deleteMeeting(meeting.meetings)
                            }
                        })
                        lifecycleScope.launch {
                            viewModel.addMeeting(meeting).await()
                            val meetingId = viewModel.returnedId
                            for (extraOption in extraOptions) {
                                extraOption.meetingId = meetingId.toInt()
                                viewModel.addCustomOption(extraOption)
                            }
                        }
                        finish()
                    }
                    true
                }
                R.id.more -> {

                    true
                }
                else -> false
            }
        }
        topAppBar.setNavigationOnClickListener {
            finish()
        }
    }
}