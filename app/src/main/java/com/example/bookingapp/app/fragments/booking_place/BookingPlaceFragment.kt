package com.example.bookingapp.app.fragments.booking_place

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bookingapp.R

class BookingPlaceFragment : Fragment() {

    private lateinit var viewModel: BookingPlaceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_booking_place, container, false)
    }

}