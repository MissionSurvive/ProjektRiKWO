package com.example.meetingapp

import android.os.Bundle
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class EventAddActivity : AppCompatActivity() {
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
    }
}