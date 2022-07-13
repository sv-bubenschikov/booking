package com.example.bookingapp.app.fragments.booking_date

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookingapp.R
import com.example.bookingapp.databinding.FragmentBookingDateBinding
import com.example.bookingapp.domain.entities.DateWeek
import com.google.android.material.chip.Chip

class BookingDateFragment : Fragment(R.layout.fragment_booking_date) {

    private val viewModel: BookingDateViewModel by viewModels()
    private lateinit var binding: FragmentBookingDateBinding
    private val dateAdapter: DateAdapter = DateAdapter()

    private val fakeDates = listOf(
        DateWeek("1", "ПН"),
        DateWeek("2", "ВТ"),
        DateWeek("3", "СР"),
        DateWeek("4", "ЧТ"),
        DateWeek("5", "ПТ"),
        DateWeek("6", "СБ"),
        DateWeek("7", "ВС"),
        DateWeek("8", "ПН"),
        DateWeek("9", "ВТ"),
        DateWeek("10", "СР"),
        DateWeek("11", "ЧТ"),
        DateWeek("12", "ПТ"),
        DateWeek("13", "СБ"),
        DateWeek("14", "ВС")
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBookingDateBinding.bind(view)

        val supportActionBar = (activity as AppCompatActivity).supportActionBar
        supportActionBar?.title = "Компания"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dateAdapter.addList(fakeDates)

        with(binding) {
            //для проверки
            btnSelect.setOnClickListener {
                val chip = layoutInflater.inflate(R.layout.chip_layout, groupDay, false) as Chip
                chip.text = "8:00 - 8:30"

                val chip2 = layoutInflater.inflate(R.layout.chip_layout, groupDay, false) as Chip
                chip2.text = "8:00 - 8:30"

                val chip3 = layoutInflater.inflate(R.layout.chip_layout, groupDay, false) as Chip
                chip3.text = "8:00 - 8:30"
                groupDay.addView(chip)
                groupEvening.addView(chip2)
                groupMorning.addView(chip3)
            }

            recFilter.apply {
                adapter = dateAdapter
                layoutManager = LinearLayoutManager(
                    this@BookingDateFragment.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }

        }
    }

}