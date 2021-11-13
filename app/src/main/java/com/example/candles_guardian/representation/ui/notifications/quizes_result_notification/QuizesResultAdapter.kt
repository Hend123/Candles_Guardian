package com.example.candles_guardian.representation.ui.notifications.quizes_result_notification

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.candles_guardian.databinding.QuizResultBinding
import com.example.candles_guardian.pojo.QuizResult

class QuizesResultAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var quizesResultNotificationList: List<QuizResult>
    private lateinit var context: Context


    init {
        quizesResultNotificationList = ArrayList()
    }


    fun setDataAndContext(quizesResultNotificationList: List<QuizResult>, context: Context) {
        this.quizesResultNotificationList = quizesResultNotificationList
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = QuizResultBinding.inflate(inflater, parent, false)
        return QuizesResultItemsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return quizesResultNotificationList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as QuizesResultItemsViewHolder
        viewHolder.bind(quizesResultNotificationList[position])
    }

    inner class QuizesResultItemsViewHolder(val binding: QuizResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: QuizResult) {
            binding.quizeResultItem = item
            binding.executePendingBindings()
        }


    }
}