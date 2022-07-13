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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentBookingDateBinding.bind(view)
        val dateAdapter = DateAdapter()

        val supportActionBar = (activity as AppCompatActivity).supportActionBar
        supportActionBar?.title = "Компания"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dateAdapter.addList(viewModel.getDates())

        val curDateList = viewModel.getDateMorning()


        with(binding) {
            //для проверки
            for (time in curDateList) {
                val chip = layoutInflater.inflate(R.layout.chip_layout, groupDay, false) as Chip
                chip.text = time
                groupMorning.addView(chip)
            }
            for (time in curDateList) {
                val chip = layoutInflater.inflate(R.layout.chip_layout, groupDay, false) as Chip
                chip.text = time
                groupDay.addView(chip)
            }
            for (time in curDateList) {
                val chip = layoutInflater.inflate(R.layout.chip_layout, groupDay, false) as Chip
                chip.text = time
                groupEvening.addView(chip)
            }

            btnSelect.setOnClickListener {
                //переход дальше
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