package com.example.bookingapp.app.fragments.booking

import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapp.databinding.BookingItemBinding
import com.example.bookingapp.domain.entities.Booking
import org.joda.time.DateTime

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
        companyTitle.text = "сначала найти место по placeId, затем найти компанию по companyId у этого места"
        bookingDescription.text = data.bookingName
        placeName.text = data.placeId // find place by id
        val date = DateTime(data.bookingDate)
        val startTime = DateTime(data.startTime)
        val endTime = DateTime(data.endTime)
        dateTime.text = "${startTime.toLocalTime()} - ${endTime.toLocalTime()}, ${date.toLocalDate()}"
    }
}