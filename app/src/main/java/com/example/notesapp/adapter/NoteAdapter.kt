package com.example.notesapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.Fragments.HomeFragment
import com.example.notesapp.Fragments.UpdateFragment
import androidx.navigation.findNavController
import androidx.navigation.NavDirections
import com.example.notesapp.Fragments.HomeFragmentDirections

import com.example.notesapp.databinding.NoteLayoutBinding
import com.example.notesapp.model.Note
import java.util.Random


class NoteAdapter:RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    class NoteViewHolder(val itembinding:NoteLayoutBinding):
            RecyclerView.ViewHolder(itembinding.root)

          private val differCallback=object :DiffUtil.ItemCallback<Note>(){//it handles smoothly the insertion deletion
          // and updating in recyclerview
              override fun areItemsTheSame(olditem: Note, newitem: Note): Boolean {
                  return olditem.id==newitem.id &&
                          olditem.notebody==newitem.notebody&&
                          olditem.notetitle==newitem.notetitle
              }

              override fun areContentsTheSame(olditem: Note, newitem: Note): Boolean {
                  return olditem==newitem
              }

          }
        val differ=AsyncListDiffer(this,differCallback)


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteLayoutBinding.inflate(
             LayoutInflater.from(p0.context),p0,false)
            )

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(p0: NoteViewHolder, p1: Int) {
        val currentNote = differ.currentList[p1]
        p0.itembinding.tvNoteTitle.text = currentNote.notetitle
        p0.itembinding.tvNoteBody.text = currentNote.notebody

        val random = Random()
        val color = Color.argb(
            255, random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256),
        )
        p0.itembinding.ibColor.setBackgroundColor(color)

        p0.itemView.setOnClickListener {
            val direction=HomeFragmentDirections.actionHomeFragmentToUpdateFragment(
                currentNote
            )
            it.findNavController().navigate(direction)
        }
    }


}
