package com.example.bookingapp.app.fragments.booking

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.app.fragments.companies.CompaniesFragment
import com.example.bookingapp.databinding.FragmentBookingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingFragment : Fragment(R.layout.fragment_booking) {

    private val viewModel: BookingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBookingBinding.bind(view)

        val supportActionBar = (activity as AppCompatActivity).supportActionBar
        supportActionBar?.title = "Запланировано"
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        binding.addBooking.setOnClickListener {
            findNavController(view).navigate(R.id.action_bookingFragment_to_companiesFragment)
        }
    }
}