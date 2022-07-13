package com.example.bookingapp.app.fragments.booking_date

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.bookingapp.R
import com.example.bookingapp.databinding.FragmentBookingDateBinding
import com.google.android.material.chip.Chip

class BookingDateFragment : Fragment(R.layout.fragment_booking_date) {

    private val viewModel: BookingDateViewModel by viewModels()
    private lateinit var binding: FragmentBookingDateBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBookingDateBinding.bind(view)

        val supportActionBar = (activity as AppCompatActivity).supportActionBar
        supportActionBar?.title = "Компания"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

        }
    }

}