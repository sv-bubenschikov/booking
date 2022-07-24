package com.example.bookingapp.app.fragments.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.bookingapp.databinding.BookingItemBinding
import com.example.bookingapp.domain.entities.Booking

class BookingListAdapter(
    private val onItemClicked: (String) -> Unit
) : ListAdapter<Booking, BookingListViewHolder>(BookingDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BookingListViewHolder(
            BookingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClicked
        )

    override fun onBindViewHolder(holder: BookingListViewHolder, position: Int) =
        holder.bind(getItem(position))
}