package com.example.bookingapp.app.fragments.booking

import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapp.databinding.BookingItemBinding
import com.example.bookingapp.domain.entities.Booking

class BookingListViewHolder(
    private val binding: BookingItemBinding,
    private val onItemClicked: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var data: Booking

    init {
        itemView.setOnClickListener {
            onItemClicked(data.id)
        }
    }

    fun bind(item: Booking) = with(binding) {
        data = item
        // TODO: #50
        companyTitle.text = data.companyName
    }
}