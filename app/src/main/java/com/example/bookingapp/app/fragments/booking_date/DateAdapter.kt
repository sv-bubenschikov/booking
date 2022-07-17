package com.example.bookingapp.app.fragments.booking_date

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapp.databinding.CardDateBinding
import com.example.bookingapp.domain.entities.Day
import org.joda.time.DateTime

class DateAdapter(private val clickListener: (Int) -> Unit) :
    RecyclerView.Adapter<DateAdapter.DateViewHolder>() {

    private var dates: List<Day> = emptyList()

    class DateViewHolder(val viewBinding: CardDateBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val binding = CardDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val item = dates[position]
        with(holder.viewBinding) {

            date.text = DateTime(item.date).dayOfMonth().asText
            dayWeek.text = when (DateTime(item.date).dayOfWeek().asText) {
                "Monday" -> "Пн"
                "Tuesday" -> "Вт"
                "Wednesday" -> "Ср"
                "Thursday" -> "Чт"
                "Friday" -> "Пт"
                "Saturday" -> "Сб"
                "Sunday" -> "Вс"
                else -> "ER"
            }
        }
        holder.itemView.setOnClickListener {
            clickListener.invoke(item.id)
        }
    }

    override fun getItemCount(): Int {
        return dates.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: List<Day>) {
        dates = list
        notifyDataSetChanged()
    }

}