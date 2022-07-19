package com.example.bookingapp.app.fragments.booking_date

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bookingapp.R
import com.example.bookingapp.app.entities.PeriodForFragment
import com.example.bookingapp.databinding.FragmentBookingDateBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingDateFragment : Fragment(R.layout.fragment_booking_date) {

    private val viewModel: BookingDateViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBookingDateBinding.bind(view)

        val dateAdapter = DateAdapter { id ->
            onDayClicked(id, binding)
        }

        //placeId - затычка
        dateAdapter.submitList(viewModel.getDays(1))

        //dayId - затычка
        val periods = viewModel.getPeriods(1)
        setPeriods(periods, binding)

        with(binding) {
            btnSelect.setOnClickListener {
                //переход дальше
            }

            recFilter.apply {
                adapter = dateAdapter
            }
        }
    }

    private fun setPeriods(periods: List<PeriodForFragment>, binding: FragmentBookingDateBinding) {
        with(binding) {
            groupMorning.removeAllViews()
            groupDay.removeAllViews()
            groupEvening.removeAllViews()

            for (period in periods) {
                val chip = Chip(this@BookingDateFragment.context)
                chip.text = period.toString()

                val timeStart = period.timeStart
                    .hourOfDay()
                    .get()

                if (timeStart < 12)//если меньше 12 -> утреннее время
                    groupMorning.addView(chip)
                else if (timeStart < 17)//если меньше 17 -> дневное время
                    groupDay.addView(chip)
                else//-> вечернее время
                    groupEvening.addView(chip)
            }
        }
    }

    private fun onDayClicked(dayId: Int, binding: FragmentBookingDateBinding) {
        setPeriods(viewModel.getPeriods(dayId), binding)
    }
}