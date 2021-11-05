package com.example.candles_guardian.representation.ui.notifications.quizes_notification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.candles_guardian.databinding.ChildernItemBinding
import com.example.candles_guardian.databinding.QuizesItemBinding
import com.example.candles_guardian.pojo.HWNotification
import com.example.candles_guardian.pojo.Stu

class QuizesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var hWNotificationList: List<HWNotification>
    private lateinit var context: Context
    private lateinit var onItemClickListener: OnItemClickListener


    init {
        hWNotificationList = ArrayList()
    }

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    fun setOnItemClickListener(onItemClickListLener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListLener
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
        var viewHolder = holder as QuizesItemsViewHolder
        viewHolder.bind(hWNotificationList[position])
    }

    inner class QuizesItemsViewHolder(val binding: QuizesItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(item: HWNotification) {
            //binding.stuItem = item
            binding.root.setOnClickListener(this)
            binding.root.setClickable(true)
            //binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(adapterPosition)
            }
        }
    }
}