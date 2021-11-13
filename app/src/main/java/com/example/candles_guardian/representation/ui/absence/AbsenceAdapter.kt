package com.example.candles_guardian.representation.ui.absence

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.candles_guardian.databinding.AbsenceItemBinding
import com.example.candles_guardian.pojo.Absence

class AbsenceAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var absenceList: List<Absence>
    private lateinit var context: Context


    init {
        absenceList = ArrayList()
    }

   fun setDataAndContext(absenceList: List<Absence>, context: Context) {
        this.absenceList = absenceList
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AbsenceItemBinding.inflate(inflater, parent, false)
        return AbsenceItemsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return absenceList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = holder as AbsenceItemsViewHolder
        viewHolder.bind(absenceList[position])
    }

    inner class AbsenceItemsViewHolder(val binding: AbsenceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Absence) {
            binding.absenceItem = item
            binding.executePendingBindings()
        }


    }
}