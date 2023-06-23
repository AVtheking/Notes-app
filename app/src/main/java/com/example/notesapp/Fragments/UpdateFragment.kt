package com.example.notesapp.Fragments

import android.app.AlertDialog
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
import androidx.navigation.fragment.navArgs
import com.example.notesapp.MainActivity

import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentNewNoteBinding
import com.example.notesapp.databinding.FragmentUpdateBinding
import com.example.notesapp.model.Note
import com.example.notesapp.viewModel.NoteViewModel


class UpdateFragment : Fragment() {
    private  var _binding: FragmentUpdateBinding?=null
    private val binding get()=_binding!!
    private lateinit var notesViewModel: NoteViewModel
    private lateinit var currentNote: Note
    private val args:UpdateFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentUpdateBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel=(activity as MainActivity).noteViewModel
        currentNote=args.note!!
        binding.etNoteTitleUpdate.setText(currentNote.notetitle)
        binding.etNoteBodyUpdate.setText(currentNote.notebody)
        binding.fabDone.setOnClickListener {
            val title=binding.etNoteTitleUpdate.text.toString().trim()
            val body=binding.etNoteBodyUpdate.text.toString().trim()
            if(title.isNotEmpty())
            {
                val note=Note(currentNote.id,title,body)
                notesViewModel.updateNote(note)
                view.findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
            }
            else{
                Toast.makeText(context,"Please enter the note title",Toast.LENGTH_LONG).show()
            }

        }
    }
    private fun  deleteNote()
    {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("You want to delete this Note?")
            setPositiveButton("Delete"){_,_->
                notesViewModel.deleteNote(currentNote)
                view?.findNavController()?.navigate(R.id.action_updateFragment_to_homeFragment)
            }
            setNegativeButton("Cancel",null)
        }.create().show()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_update,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       when(item.itemId){
           R.id.menu_delete->
               deleteNote()
       }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}