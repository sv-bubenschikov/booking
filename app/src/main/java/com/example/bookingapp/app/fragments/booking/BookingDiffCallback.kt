package com.example.bookingapp.app.fragments.booking

import androidx.recyclerview.widget.DiffUtil
import com.example.bookingapp.domain.entities.Booking

object BookingDiffCallback : DiffUtil.ItemCallback<Booking>() {

    override fun areItemsTheSame(oldItem: Booking, newItem: Booking) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Booking, newItem: Booking) =
        oldItem == newItem
}