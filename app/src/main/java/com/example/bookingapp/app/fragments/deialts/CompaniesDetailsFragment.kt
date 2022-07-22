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
import com.example.bookingapp.databinding.FragmentPlacesBinding
import kotlinx.coroutines.launch

class CompaniesDetailsFragment : Fragment(R.layout.fragment_places) {

    private val hostViewModel: HostViewModel by activityViewModels()
    private val viewModel: CompaniesDetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentPlacesBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.company.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect {
            }
        }

        hostViewModel.setActionButtonVisible(false)
    }

    companion object {
        const val Company_Id = "company_id"
    }
}