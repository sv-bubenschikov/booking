package com.example.bookingapp.app.fragments.booking_date

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.bookingapp.databinding.CardDateBinding
import com.example.bookingapp.domain.entities.Day

class DateAdapter(private val clickListener: (Int) -> Unit) :
    ListAdapter<Day, DateViewHolder>(DayDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val binding = CardDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DateViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) =
        holder.bind(getItem(position))

}