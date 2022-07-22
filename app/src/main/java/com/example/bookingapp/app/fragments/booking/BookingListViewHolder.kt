package com.example.bookingapp.app.fragments.booking

import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapp.databinding.BookingItemBinding
import com.example.bookingapp.domain.entities.Booking

class BookingListViewHolder(
    private val binding: BookingItemBinding,
    private val onItemClicked: (Booking) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var data: Booking

    init {
        itemView.setOnClickListener {
            onItemClicked(data)
        }
    }

    fun bind(item: Booking) = with(binding) {
        data = item
        companyTitle.text = data.company
    }
}