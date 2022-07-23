package com.example.bookingapp.app.fragments.deialts

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.bookingapp.R
import com.example.bookingapp.app.HostViewModel
import com.example.bookingapp.databinding.FragmentBookingDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.joda.time.DateTime

@AndroidEntryPoint
class BookingDetailsFragment : Fragment(R.layout.fragment_booking_details) {

    private val hostViewModel: HostViewModel by activityViewModels()
    private val viewModel: BookingDetailsViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        hostViewModel.setActionButtonVisible(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentBookingDetailsBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.booking.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { booking ->
                binding.bookingOrganisationBlockText.text = booking.companyName
                binding.bookingPlaceBlockText.text = booking.placeName
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.bookingTime.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { time ->
                binding.bookingDatetimeBlockText.text = time
            }
        }
    }

    companion object {
        const val BOOKING_ID = "booking_id"
    }
}