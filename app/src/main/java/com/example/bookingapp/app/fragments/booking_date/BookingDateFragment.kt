package com.example.bookingapp.app.fragments.booking_date

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.bookingapp.R
import com.example.bookingapp.app.entities.PeriodForFragment
import com.example.bookingapp.databinding.FragmentBookingDateBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookingDateFragment : Fragment(R.layout.fragment_booking_date) {

    private val viewModel: BookingDateViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBookingDateBinding.bind(view)

        val dateAdapter = DateAdapter { dayId ->
            viewModel.onDayClicked(dayId)
        }

        with(binding) {
            btnSelect.setOnClickListener {
                //findNavController().navigate(R.id.action_bookingDateFragment_to_bookingDetailsFragment)
            }

            recFilter.adapter = dateAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.days.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { dayList ->
                dateAdapter.submitList(dayList)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.periods.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { periodList ->
                    setPeriods(periodList, binding)
                }
        }
    }

    private fun setPeriods(periods: List<PeriodForFragment>, binding: FragmentBookingDateBinding) {
        with(binding) {
            groupMorning.removeAllViews()
            groupDay.removeAllViews()
            groupEvening.removeAllViews()

            for (period in periods) {
                val chip = Chip(requireContext())
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

    companion object {
        const val PLACE_ID = "place_id"
    }
}