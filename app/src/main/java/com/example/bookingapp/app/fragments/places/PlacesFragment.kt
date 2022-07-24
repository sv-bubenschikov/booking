package com.example.bookingapp.app.fragments.places

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.app.HostViewModel
import com.example.bookingapp.databinding.FragmentPlacesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlacesFragment : Fragment(R.layout.fragment_places) {

    private val hostViewModel: HostViewModel by activityViewModels()
    private val viewModel: PlacesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentPlacesBinding.bind(view)

        val placeListAdapter = PlaceListAdapter { place ->
            val arg = Bundle().apply {
                val booking = viewModel.booking
                booking.placeId = place.id
                booking.placeName = place.name
                putParcelable(BOOKING, booking)
            }
            findNavController(view).navigate(
                R.id.action_placesFragment_to_bookingDateFragment,
                arg
            )
        }

        hostViewModel.setToolbarTitle(viewModel.booking.companyName)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.placesAndFeatures.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { (places, features) ->
                    placeListAdapter.submitList(places.toMutableList())
                    viewModel.setFilters(binding, features, placeListAdapter)
                }
        }
        
        binding.recyclePlaces.adapter = placeListAdapter
    }

    companion object {
        const val BOOKING = "booking"
    }
}