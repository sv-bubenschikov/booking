package com.example.bookingapp.app.fragments.places

import androidx.recyclerview.widget.DiffUtil
import com.example.bookingapp.domain.entities.Place

object PlaceDiffCallback : DiffUtil.ItemCallback<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Place, newItem: Place) =
        oldItem == newItem
}