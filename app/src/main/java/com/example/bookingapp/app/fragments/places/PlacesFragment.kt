package com.example.bookingapp.app.fragments.places

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.app.fragments.booking_place.BookingPlaceFragment.Companion.PLACE_ID
import com.example.bookingapp.databinding.FragmentPlacesBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlacesFragment : Fragment(R.layout.fragment_places) {

    private val viewModel: PlacesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentPlacesBinding.bind(view)

        val placeListAdapter = PlaceListAdapter { place ->
            val arg = Bundle().apply {
                putInt(PLACE_ID, place.id)
            }
            findNavController(view).navigate(
                R.id.action_placesFragment_to_bookingPlaceFragment,
                arg
            )
        }

        with(binding) {
            recyclePlaces.adapter = placeListAdapter

            // Пока что добавил заглушку, чтобы можно было проверить верстку
            val attributes = listOf("1 этаж", "2 этаж", "3 этаж", "Переговорка", "4 этаж", "5 этаж")
            for (attr in attributes) {
                val chip =
                    layoutInflater.inflate(R.layout.chip_filter_layout, chipsFilter, false) as Chip
                chip.text = attr
                chipsFilter.addView(chip)
            }
        }

        // TODO #27

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.places.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { places ->
                placeListAdapter.submitList(places)
            }
        }
    }

    companion object {
        const val COMPANY_ID = "company_id"
        const val COMPANY_TITLE = "company_title"
    }
}