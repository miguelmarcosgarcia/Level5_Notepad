package com.example.notepad_level5.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.notepad_level5.R

import android.content.Intent
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        fab.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra(EditActivity.EXTRA_NOTE, mainActivityViewModel.note.value)
            startActivity(intent)
        }
    }


    private fun initViewModel() {
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        mainActivityViewModel.note.observe(this, Observer {
                note ->
            if (note != null) {
                tvTitle.text = note.title
                tvLastUpdated.text = getString(R.string.last_updated, note.lastUpdated.toString())
                tvNote.text = note.text
            }
        })
    }
}