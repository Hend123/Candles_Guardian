package com.example.candles_guardian.representation.ui.notes

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.candles_guardian.databinding.StuBehaviourNoteItemBinding
import com.example.candles_guardian.pojo.StuBehaviourNote

class NotesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var noteList: List<StuBehaviourNote>
    private lateinit var context: Context


    init {
        noteList = ArrayList()
    }




    fun setDataAndContext(noteList: List<StuBehaviourNote>, context: Context) {
        this.noteList = noteList
        this.context = context
        //notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StuBehaviourNoteItemBinding.inflate(inflater, parent, false)
        return NoteItemsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = holder as NoteItemsViewHolder
        viewHolder.bind(noteList[position])
    }

    inner class NoteItemsViewHolder(val binding: StuBehaviourNoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StuBehaviourNote) {
            binding.stuNoteItem = item
            binding.executePendingBindings()
        }

    }
}