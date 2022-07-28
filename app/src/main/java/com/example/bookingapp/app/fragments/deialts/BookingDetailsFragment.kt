package com.example.bookingapp.app.fragments.deialts

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.app.HostViewModel
import com.example.bookingapp.databinding.FragmentBookingDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookingDetailsFragment : Fragment(R.layout.fragment_booking_details) {

    private val hostViewModel: HostViewModel by activityViewModels()
    private val viewModel: BookingDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentBookingDetailsBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.booking
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { booking ->
                    binding.bookingCompanyBlockText.text = booking.companyName
                    binding.bookingPlaceBlockText.text = booking.placeName
                    binding.bookingThemeBlockText.text = booking.theme
                    if (!viewModel.isFromDateFragment && booking.theme == "") {
                        binding.bookingThemeBlockTitle.visibility = View.GONE
                    } else {
                        binding.bookingThemeBlockTitle.visibility = View.VISIBLE
                    }
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.bookingTime
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { time ->
                    binding.bookingDatetimeBlockText.text = time
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.bookingDecision
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { decision ->
                    val destination = when (decision) {
                        BookingDecision.CONFIRM -> R.id.action_bookingDetailsFragment_to_bookingFragment
                        BookingDecision.CANCEL -> R.id.action_bookingDetailsFragment_to_bookingFragment
                    }
                    findNavController().navigate(destination)
                }
        }

        if (viewModel.isFromDateFragment) {
            binding.confirmBookingButton.setOnClickListener {
                viewModel.onConfirmBookingClicked()
            }
            binding.cancelBookingButton.visibility = View.GONE
            binding.bookingThemeBlockText.visibility = View.GONE
        }
        else {
            binding.cancelBookingButton.setOnClickListener {
                viewModel.onCancelBookingClicked()
            }
            binding.confirmBookingButton.visibility = View.GONE
            binding.bookingThemeBlockInputLayout.visibility = View.GONE
        }

        binding.bookingThemeBlockInputText.doOnTextChanged { text, _, _, _ ->
            viewModel.onBookingThemeInputChanged(text.toString())
        }
    }

    override fun onStart() {
        super.onStart()

        hostViewModel.setActionButtonVisible(false)
        // TODO: #47
    }

    companion object {
        const val BOOKING = "booking"
        const val BOOKING_ID = "booking_id"
    }
}