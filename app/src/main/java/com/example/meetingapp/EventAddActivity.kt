package com.example.meetingapp

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meetingapp.adapters.ExtraOptionsListAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Date

class EventAddActivity : AppCompatActivity() {

    var startDateSelection: Long = 0
    var endDateSelection: Long = 0
    var extraOptions: MutableList<CustomOptions> = mutableListOf()
    var startUnixTime: Long = 0
    var endUnixTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_event_add)



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

        //start date event

        startDateInput.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
            val constraintsBuilder =
                CalendarConstraints.Builder()
                    .setValidator(DateValidatorPointForward.now())
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
                    startDateSelection = datePicker.selection!!
                    var minuteSelection: Long = timePicker.minute.toLong()
                    var hourSelection: Long = timePicker.hour.toLong()

                    minuteSelection *= 60000
                    hourSelection *= 3600000

                    startUnixTime = startDateSelection + hourSelection + minuteSelection

                    startDateInput.setText(Date(startUnixTime).toString())
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
                val constraintsBuilder =
                    CalendarConstraints.Builder()
                        .setValidator(DateValidatorPointForward.from(startDateSelection))
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

                        endDateInput.setText(Date(endUnixTime).toString())
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

                    if (!optionContentTextField.text.isNullOrEmpty() || !optionNameTextField.text.isNullOrEmpty()) {
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

                    if (nameInput.text.isNullOrEmpty() || startUnixTime == 0L || endUnixTime == 0L) {
                        Toast.makeText(this, "Wszystkie pola poza opisem wydarzenia muszą być wypełnione.", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        val meeting: Meetings
                        if (descriptionInput.text.isNullOrEmpty()) {
                            meeting = Meetings(0, nameInput.text.toString(), "null", Date(startUnixTime), (startUnixTime-endUnixTime).toInt(), checkBox.isChecked)
                        }
                        else {
                            meeting = Meetings(0, nameInput.text.toString(), descriptionInput.text.toString(), Date(startUnixTime), (startUnixTime-endUnixTime).toInt(), checkBox.isChecked)
                            null
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
    }
}