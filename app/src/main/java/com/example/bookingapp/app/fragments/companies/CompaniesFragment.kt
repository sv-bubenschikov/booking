package com.example.bookingapp.app.fragments.companies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.app.MainActivity
import com.example.bookingapp.databinding.FragmentCompaniesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompaniesFragment : Fragment(R.layout.fragment_companies) {

    private val viewModel: CompaniesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCompaniesBinding.bind(view)

        binding.buttonNextFragment.setOnClickListener {
            findNavController(view).navigate(R.id.action_companiesFragment_to_placesFragment)
        }
    }
}