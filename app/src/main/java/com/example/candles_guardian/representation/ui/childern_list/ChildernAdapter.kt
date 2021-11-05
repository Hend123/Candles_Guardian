package com.example.candles_guardian.representation.ui.childern_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.candles_guardian.databinding.ChildernItemBinding
import com.example.candles_guardian.pojo.Stu

class ChildernAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var stuList: List<Stu>
    private lateinit var context: Context
    private lateinit var onItemClickListener: OnItemClickListener


    init {
        stuList = ArrayList()
    }

    interface OnItemClickListener {
        //fun onClick(position: Int)
        fun onClickingMenu(position: Int,v:View)

    }

    fun setOnItemClickListener(onItemClickListLener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListLener
    }


    fun setDataAndContext(stuList: List<Stu>, context: Context) {
        this.stuList = stuList
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val binding = ChildernItemBinding.inflate(inflater)
        val binding = ChildernItemBinding.inflate(inflater, parent, false)
        return ChildernItemsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return stuList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder = holder as ChildernItemsViewHolder
        viewHolder.bind(stuList[position])
    }

    inner class ChildernItemsViewHolder(val binding: ChildernItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(item: Stu) {
            binding.stuItem = item
//            binding.root.setOnClickListener(this)
//            binding.root.setClickable(true)
            binding.menuIc.setOnClickListener(this)
            binding.menuIc.isClickable = true
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            if (onItemClickListener != null) {
                if (v == binding.menuIc) {
                    onItemClickListener.onClickingMenu(adapterPosition,v)
//                }else{
//                    onItemClickListener.onClick(adapterPosition)
//
//                }
                }
            }
        }
    }
}