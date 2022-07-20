package com.example.bookingapp.app.fragments.deialts

import android.os.Bundle
import android.view.View
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
            viewModel.booking.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { booking ->
                binding.bookingCompanyBlockText.text = booking.company.name
                binding.bookingPlaceBlockText.text = booking.place.name
                binding.bookingDatetimeBlockText.text = booking.bookingDate.toString()
                binding.bookingNameBlockText.text = booking.bookingName
            }
        }

        binding.cancelBookingButton.setOnClickListener {
            viewModel.onCancelBookingClicked().invokeOnCompletion {
                findNavController().navigate(R.id.action_bookingDetailsFragment_to_bookingFragment)
            }
        }

        binding.confirmBookingButton.setOnClickListener {
            viewModel.onConfirmBookingClicked().invokeOnCompletion {
                findNavController().navigate(R.id.action_bookingDetailsFragment_to_bookingFragment)
            }
        }

        binding.returnToDateChoosingButton.setOnClickListener {
            //TODO: Добавить переход на экран выбора даты
        }

        hostViewModel.setActionButtonVisible(false)
    }

    companion object {
        const val BOOKING_ID = "booking_id"
    }
}