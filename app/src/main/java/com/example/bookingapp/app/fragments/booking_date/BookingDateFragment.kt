package com.example.bookingapp.app.fragments.booking_date

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.app.HostViewModel
import com.example.bookingapp.app.entities.PeriodForFragment
import com.example.bookingapp.databinding.FragmentBookingDateBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookingDateFragment : Fragment(R.layout.fragment_booking_date) {

    private val hostViewModel: HostViewModel by activityViewModels()
    private val viewModel: BookingDateViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBookingDateBinding.bind(view)

        val dateAdapter = DateAdapter { day ->
            viewModel.onDayClicked(day)
        }

        with(binding) {
            btnSelect.setOnClickListener {
                viewModel.onCompleteClicked()
            }
            recFilter.adapter = dateAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.complete.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { draft ->
                val arg = Bundle().apply {
                    putParcelable(BOOKING, draft)
                }
                findNavController().navigate(
                    R.id.action_bookingDateFragment_to_bookingDetailsFragment,
                    arg
                )
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorMessage.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { messageId ->
                    Toast.makeText(requireContext(), messageId, Toast.LENGTH_SHORT).show()
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.days.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { dayList ->
                dateAdapter.submitList(dayList)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedDay.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
                dateAdapter.notifyDataSetChanged()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.periods.combine(viewModel.selectedPeriod) { periods, selected ->
                periods to selected
            }
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { (periods, selected) ->
                    setPeriod(binding, periods, selected, viewModel::onPeriodClicked)
                }
        }
    }

    private fun setPeriod(
        binding: FragmentBookingDateBinding,
        periods: List<PeriodForFragment>,
        selected: PeriodForFragment?,
        onChipClicked: (PeriodForFragment) -> Unit
    ) {
        with(binding) {
            groupMorning.removeAllViews()
            groupDay.removeAllViews()
            groupEvening.removeAllViews()

            for (period in periods) {
                val chip = Chip(requireContext())
                chip.text = period.toString()

                chip.isSelected =
                    if (selected != null)
                        selected == period
                    else false

                chip.setOnClickListener {
                    onChipClicked(period)
                }

                val timeStart = period.timeStart
                    .hourOfDay()
                    .get()

                if (timeStart < 12) //если меньше 12 -> утреннее время
                    groupMorning.addView(chip)
                else if (timeStart < 17)//если меньше 17 -> дневное время
                    groupDay.addView(chip)
                else //-> вечернее время
                    groupEvening.addView(chip)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        hostViewModel.setActionButtonVisible(false)
        hostViewModel.setToolbarTitle(
            viewModel.booking.placeName.takeIf { it.isNotEmpty() } ?: viewModel.booking.placeType
        )
    }

    companion object {
        const val BOOKING = "booking"
    }
}