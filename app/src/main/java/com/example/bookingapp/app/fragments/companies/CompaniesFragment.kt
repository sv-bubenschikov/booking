package com.example.bookingapp.app.fragments.companies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.bookingapp.R
import com.example.bookingapp.databinding.FragmentCompaniesBinding

class CompaniesFragment : Fragment(R.layout.fragment_companies) {

    private val viewModel: CompaniesViewModel by viewModels()
    private lateinit var binding: FragmentCompaniesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCompaniesBinding.bind(view)

        requireActivity().actionBar?.title = "Компании"
    }
}