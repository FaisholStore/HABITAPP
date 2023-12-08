package com.dicoding.habitapp.ui.random

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.habitapp.R
import com.dicoding.habitapp.data.Habit
import com.dicoding.habitapp.databinding.PagerItemBinding

class RandomHabitAdapter(
    private val onClick: (Habit) -> Unit
) : RecyclerView.Adapter<RandomHabitAdapter.PagerViewHolder>() {

    private val habitMap = LinkedHashMap<PageType, Habit>()

    fun submitData(key: PageType, habit: Habit) {
        habitMap[key] = habit
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val binding = PagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val key = getIndexKey(position) ?: return
        val pageData = habitMap[key] ?: return
        holder.bind(key, pageData)
    }

    override fun getItemCount() = habitMap.size

    private fun getIndexKey(position: Int) = habitMap.keys.toTypedArray().getOrNull(position)

    enum class PageType {
        HIGH, MEDIUM, LOW
    }

    inner class PagerViewHolder(private val binding: PagerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //TODO 14 : Create view and bind data to item view
        fun bind(pageType: PageType, pageData: Habit) {
            binding.pagerTvTitle.text = pageData.title
            binding.pagerTvStartTime.text = pageData.startTime
            binding.pagerTvMinutes.text = pageData.minutesFocus.toString()

            when (pageType) {
                PageType.HIGH -> binding.pagerPriorityLevel.setImageResource(R.drawable.ic_priority_high)
                PageType.MEDIUM -> binding.pagerPriorityLevel.setImageResource(R.drawable.ic_priority_medium)
                else -> binding.pagerPriorityLevel.setImageResource(R.drawable.ic_priority_low)
            }

            binding.btnOpenCountDown.setOnClickListener { onClick(pageData) }
        }
    }
}