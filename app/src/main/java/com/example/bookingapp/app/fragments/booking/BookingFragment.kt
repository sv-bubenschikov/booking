package com.example.bookingapp.app.fragments.booking

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.example.bookingapp.R
import com.example.bookingapp.app.fragments.companies.CompaniesFragment
import com.example.bookingapp.databinding.FragmentBookingBinding

class BookingFragment : Fragment(R.layout.fragment_booking) {

    private val viewModel: BookingViewModel by viewModels()
    private lateinit var binding: FragmentBookingBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBookingBinding.bind(view)

        requireActivity().actionBar?.title = "Запланировано"

        binding.addBooking.setOnClickListener {
            openCompaniesFragment()
        }
    }

    private fun openCompaniesFragment(){
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, CompaniesFragment())
            .addToBackStack(null)
            .commit()
    }
}