package com.example.bookingapp.app.fragments.companies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.app.HostViewModel
import com.example.bookingapp.databinding.FragmentCompaniesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompaniesFragment : Fragment(R.layout.fragment_companies) {

    private val hostViewModel: HostViewModel by activityViewModels()
    private val viewModel: CompaniesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCompaniesBinding.bind(view)

        hostViewModel.setActionButtonVisible(false)
        binding.buttonNextFragment.setOnClickListener {
            findNavController(view).navigate(R.id.action_companiesFragment_to_placesFragment)
        }
    }
}