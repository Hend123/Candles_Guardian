package com.example.candles_guardian.representation.ui.notifications.quizes_notification

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.candles_guardian.databinding.QuizesItemBinding
import com.example.candles_guardian.pojo.HWNotification

class QuizesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var hWNotificationList: List<HWNotification>
    private lateinit var context: Context


    init {
        hWNotificationList = ArrayList()
    }

    fun setDataAndContext(hWNotificationList: List<HWNotification>, context: Context) {
        this.hWNotificationList = hWNotificationList
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = QuizesItemBinding.inflate(inflater, parent, false)
        return QuizesItemsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return hWNotificationList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as QuizesItemsViewHolder
        viewHolder.bind(hWNotificationList[position])
    }

    inner class QuizesItemsViewHolder(val binding: QuizesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HWNotification) {
            binding.quizeItem = item
            binding.executePendingBindings()
        }


    }
}