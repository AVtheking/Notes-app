package com.example.notesapp

import   androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.database.NoteDatabase
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.repository.NoteRepository
import com.example.notesapp.viewModel.NoteViewModel
import com.example.notesapp.viewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        setUpViewModel()
    }

    private fun setUpViewModel() {
        val noteRepository=NoteRepository(NoteDatabase(this))
        val noteViewModelFactory=ViewModelFactory(application,noteRepository)
        noteViewModel=ViewModelProvider(this,
        noteViewModelFactory).get(NoteViewModel::class.java)
    }
}