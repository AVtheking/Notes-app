package com.example.notesapp.Fragments

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.widget.SearchView

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.MainActivity

import com.example.notesapp.R
import com.example.notesapp.adapter.NoteAdapter
import com.example.notesapp.databinding.FragmentHomeBinding
import com.example.notesapp.model.Note
import com.example.notesapp.viewModel.NoteViewModel


class HomeFragment : Fragment(R.layout.fragment_home) ,SearchView.OnQueryTextListener{
private var _binding:FragmentHomeBinding?=null
    private val binding get()=_binding!!
    private lateinit var notesViewModel: NoteViewModel
    private lateinit var notesAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }



    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      _binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel=(activity as MainActivity).noteViewModel

        setUpRecyclerView()
        binding.fabAddNote.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }
    }

    private fun setUpRecyclerView() {
       notesAdapter= NoteAdapter()
        binding.recycleview.apply{
            layoutManager=StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
            adapter=notesAdapter

        }
        activity?.let {
            notesViewModel.getAllNote().observe(
                viewLifecycleOwner,{
                    note->notesAdapter.differ.submitList(note)
                    updateUI(note)
                }
            )


        }
    }

    private fun updateUI(note: List<Note>?) {
        if (note != null) {
            if(note.isNotEmpty())
            {
                binding.cardView.visibility=View.GONE
                binding.recycleview.visibility=View.VISIBLE
            }
            else
            {
                binding.cardView.visibility=View.VISIBLE
                binding.recycleview.visibility=View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.clear()
        inflater.inflate(R.menu.home_menu,menu)
        val mMenuSearch=menu.findItem(R.id.menu_search).actionView as SearchView

        mMenuSearch.isSubmitButtonEnabled=false
        mMenuSearch.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
//        searchNote(p0)
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {
         if(p0!=null)
         {
             searchNote(p0)
         }
        return true
    }
    //these all are templates you just need to cooy and paste
    // it everywhere just do practice of them
    private fun searchNote(p0: String) {
       val searchquery="%$p0"
        notesViewModel.searchNote(searchquery).observe(
            this,
            {
                list->notesAdapter.differ.submitList(list)
            }
        )


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}