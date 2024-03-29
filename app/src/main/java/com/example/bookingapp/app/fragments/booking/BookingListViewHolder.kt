package com.example.bookingapp.app.fragments.booking

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapp.databinding.BookingItemBinding
import com.example.bookingapp.domain.entities.Booking
import org.joda.time.DateTime

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

        companyTitle.text = data.companyName

        placeName.text = data.placeName
        placeName.isVisible = placeName.text.isNotEmpty()

        theme.text = data.theme
        theme.isVisible = theme.text.isNotEmpty()

        dateTime.text = data.let {
            val dateTime = DateTime(it.bookingDate).toLocalDateTime().toString("dd-MM-yyyy")
            val startTime = DateTime(it.startTime).toLocalDateTime().toString("HH:mm")
            val endTime = DateTime(it.endTime).toLocalDateTime().toString("HH:mm")
            "$dateTime; $startTime - $endTime"
        }
    }
}