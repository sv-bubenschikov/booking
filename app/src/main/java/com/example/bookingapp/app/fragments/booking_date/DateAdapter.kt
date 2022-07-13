package com.example.bookingapp.app.fragments.booking_date

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapp.databinding.CardDateBinding
import com.example.bookingapp.domain.entities.DateWeek

class DateAdapter() : RecyclerView.Adapter<DateAdapter.DateViewHolder>() {

    private var dates: List<DateWeek> = emptyList()

    class DateViewHolder(val viewBinding: CardDateBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val binding = CardDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        with(holder.viewBinding){
            val item = dates[position]
            date.text = item.date
            dayWeek.text = item.dayOfWeek
        }
    }

    override fun getItemCount(): Int {
        return dates.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: List<DateWeek>) {
        dates = list
        notifyDataSetChanged()
    }

}