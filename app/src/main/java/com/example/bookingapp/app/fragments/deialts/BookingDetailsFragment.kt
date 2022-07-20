package com.example.bookingapp.app.fragments.deialts

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

@AndroidEntryPoint
class BookingDetailsFragment : Fragment(R.layout.fragment_booking_details) {

    private val hostViewModel: HostViewModel by activityViewModels()
    private val viewModel: BookingDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentBookingDetailsBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.booking.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { booking ->
                binding.bookingOrganisationBlockText.text = "сначала найти место по placeId, затем найти компанию по companyId у этого места"
            }
        }

        hostViewModel.setActionButtonVisible(false)
    }

    companion object {
        const val BOOKING_ID = "booking_id"
    }
}