package com.example.bookingapp.app.fragments.booking_date

import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapp.databinding.CardDateBinding
import com.example.bookingapp.domain.entities.Day
import org.joda.time.DateTime

class DateViewHolder(
    private val binding: CardDateBinding,
    private val clickListener: (Day) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var day: Day

    init {
        itemView.setOnClickListener {
            clickListener(day)
            binding.card.isChecked = day.isSelected
        }
    }

    fun bind(item: Day) = with(binding) {
        day = item
        binding.card.isChecked = day.isSelected
        date.text = DateTime(day.date).dayOfMonth().asText
        dayWeek.text = when (DateTime(day.date).dayOfWeek) {
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