package com.example.notesapp.viewModel

import android.app.Application
import com.example.notesapp.model.Note
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.notesapp.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(
    app:Application,
    private val repository: NoteRepository
):AndroidViewModel(app)
{
  fun insertNote(note:Note)
  {
      viewModelScope.launch {
          repository.insertNote(note)
      }
  }
  fun updateNote(note:Note)
  {
      viewModelScope.launch {
          repository.updateNote(note)
      }
  }
    fun deleteNote(note:Note)
    {
        viewModelScope.launch{
            repository.deleteNote(note)
        }
    }
    fun getAllNote()
    {
        repository.getallNotes()
    }
    fun searchNote(query: String?)=repository.searchNote(query)

}