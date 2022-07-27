package com.example.bookingapp.app.fragments.booking_date

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapp.databinding.CardDateBinding
import com.example.bookingapp.domain.entities.Day
import org.joda.time.DateTime

class DateAdapter(
    private val clickListener: (Day) -> Unit
) : ListAdapter<Day, DateAdapter.DateViewHolder>(DayDiffCallback) {

    lateinit var lastSelectedItem: View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val binding = CardDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DateViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class DateViewHolder(
        private val binding: CardDateBinding,
        private val clickListener: (Day) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var day: Day

        init {
            itemView.setOnClickListener {
                clickListener(day)
                lastSelectedItem.isSelected = false
                it.isSelected = true
                lastSelectedItem = it
            }
        }

        fun bind(item: Day) = with(binding) {
            day = item
            val dateTime = DateTime(day.date)

            if(day.isSelected) {
                root.isSelected = true
                lastSelectedItem = binding.root
            }

            date.text = dateTime.dayOfMonth().asText
            dayWeek.text = when (dateTime.dayOfWeek) {
                1 -> "Пн"
                2 -> "Вт"
                3 -> "Ср"
                4 -> "Чт"
                5 -> "Пт"
                6 -> "Сб"
                7 -> "Вс"
                else -> "ER"
            }
        }
    }
}