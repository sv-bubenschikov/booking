package com.example.bookingapp.app.fragments.deialts

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.bookingapp.R
import com.example.bookingapp.app.HostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingDetailsFragment : Fragment(R.layout.fragment_booking_details) {

    private val hostViewModel: HostViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hostViewModel.setActionButtonVisible(false)
    }
}