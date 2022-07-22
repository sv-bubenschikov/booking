package com.example.bookingapp.app.fragments.booking_place

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.bookingapp.R
import com.example.bookingapp.databinding.FragmentCompaniesBinding

class CompaniesPlaceFragment : Fragment(R.layout.fragment_companies) {

    private lateinit var viewModel: CompaniesPlaceViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCompaniesBinding.bind(view)

    }
}