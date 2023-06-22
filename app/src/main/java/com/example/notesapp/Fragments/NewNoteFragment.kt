package com.example.notesapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.notesapp.MainActivity

import com.example.notesapp.R
import com.example.notesapp.adapter.NoteAdapter
import com.example.notesapp.databinding.FragmentNewNoteBinding
import com.example.notesapp.model.Note
import com.example.notesapp.viewModel.NoteViewModel


class newNoteFragment : Fragment(R.layout.fragment_new_note) {
    private  var _binding:FragmentNewNoteBinding?=null
    private val binding get()=_binding!!
    private lateinit var notesViewModel: NoteViewModel
    private lateinit var notesAdapter: NoteAdapter
    private lateinit var mview: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel=(activity as MainActivity).noteViewModel
        mview=view
    }
  private fun saveNote(view: View)
  {
      val noteTitle=binding.editNoteTitle.text.toString().trim()
      val noteBody=binding.editNoteBody.text.toString().trim()
      if(noteTitle.isNotEmpty())
      {
          val note= Note(0,noteTitle,noteBody)
          notesViewModel.insertNote(note)
          Toast.makeText(mview.context,"Note saved succesfully",Toast.LENGTH_LONG).show()
          view.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)
      }
      else{
          Toast.makeText(mview.context,"Please enter the note Title",Toast.LENGTH_LONG).show()
      }


  }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_new_note,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.menu_save->
            {
                saveNote(mview)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}