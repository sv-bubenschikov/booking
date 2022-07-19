package com.example.bookingapp.app.fragments.booking_date

import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapp.databinding.CardDateBinding
import com.example.bookingapp.domain.entities.Day
import org.joda.time.DateTime

class DateViewHolder(
    private val binding: CardDateBinding,
    private val clickListener: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var day: Day

    init {
        itemView.setOnClickListener {
            clickListener(day.id)
        }
    }

    fun bind(item: Day) = with(binding) {
        day = item
        date.text = DateTime(day.date).dayOfMonth().asText
        dayWeek.text = when (DateTime(day.date).dayOfWeek().asText) {
            "Monday" -> "Пн"
            "Tuesday" -> "Вт"
            "Wednesday" -> "Ср"
            "Thursday" -> "Чт"
            "Friday" -> "Пт"
            "Saturday" -> "Сб"
            "Sunday" -> "Вс"
            else -> "ER"
        }
    }
}