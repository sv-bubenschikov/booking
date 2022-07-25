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
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
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
                val booking = viewModel.booking.copy(
                    placeId = place.id,
                    placeName = place.name,
                    placeType = place.type
                )
                putParcelable(BOOKING, booking)
            }
            findNavController(view).navigate(
                R.id.action_placesFragment_to_bookingDateFragment,
                arg
            )
        }
        binding.recyclePlaces.adapter = placeListAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.places.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { places ->
                    placeListAdapter.submitList(places.toMutableList())
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.features.combine(viewModel.selectedFeatures) { features, selected ->
                features to selected
            }
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { (features, selected) ->
                    setFilters(binding, features, selected, viewModel::onFeatureClicked)
                }
        }
    }

    private fun setFilters(
        binding: FragmentPlacesBinding,
        filters: List<String>,
        selectedFilters: List<String>,
        onChipClicked: (feature: String, isSelected: Boolean) -> Unit
    ) {
        with(binding) {
            chipsFilter.removeAllViews()
            for (filter in filters) {
                val chip = Chip(chipsFilter.context)
                chip.text = filter
                chip.isSelected = filter in selectedFilters

                // Не уверен, что правильно реализовал фильтрацию, возможно можно подругому сделать
                chip.setOnClickListener {
                    onChipClicked(chip.text.toString(), !chip.isSelected)
                }
                chipsFilter.addView(chip)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        hostViewModel.setActionButtonVisible(false)
        hostViewModel.setToolbarTitle(viewModel.booking.companyName)
    }

    companion object {
        const val BOOKING = "booking"
    }
}