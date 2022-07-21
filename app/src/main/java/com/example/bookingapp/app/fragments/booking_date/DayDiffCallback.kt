package com.example.bookingapp.app.fragments.booking_date

import androidx.recyclerview.widget.DiffUtil
import com.example.bookingapp.domain.entities.Day

object DayDiffCallback : DiffUtil.ItemCallback<Day>() {

    override fun areItemsTheSame(oldItem: Day, newItem: Day) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Day, newItem: Day) =
        oldItem == newItem
}