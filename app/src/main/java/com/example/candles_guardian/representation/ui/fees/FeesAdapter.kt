package com.example.candles_guardian.representation.ui.fees

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.candles_guardian.pojo.Fees
import com.example.candles_guardian.databinding.FeesItemBinding

class FeesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var feesList: List<Fees>
    private lateinit var context: Context


    init {
        feesList = ArrayList()
    }

   fun setDataAndContext(feesList: List<Fees>, context: Context) {
        this.feesList = feesList
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FeesItemBinding.inflate(inflater, parent, false)
        return FeesItemsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return feesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = holder as FeesItemsViewHolder
        viewHolder.bind(feesList[position])
    }

    inner class FeesItemsViewHolder(val binding: FeesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Fees) {
            binding.feesItem = item
            binding.executePendingBindings()
        }


    }
}