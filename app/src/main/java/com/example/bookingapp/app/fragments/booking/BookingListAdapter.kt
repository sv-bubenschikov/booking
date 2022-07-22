package com.example.bookingapp.app.fragments.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.bookingapp.databinding.BookingItemBinding
import com.example.bookingapp.domain.entities.Booking

class BookingListAdapter(
    private val onItemClicked: (Booking) -> Unit
) : ListAdapter<Booking, BookingListViewHolder>(BookingDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context).let { inflater ->
            BookingListViewHolder(
                BookingItemBinding.inflate(inflater, parent, false),
                onItemClicked
            )
        }

    override fun onBindViewHolder(holder: BookingListViewHolder, position: Int) =
        holder.bind(getItem(position))
}