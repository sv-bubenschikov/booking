package com.example.bookingapp.app.fragments.places

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.app.fragments.places.PlacesFragment.Companion.BOOKING
import com.example.bookingapp.databinding.FragmentPlacesBinding
import com.example.bookingapp.domain.entities.BookingBuilder
import com.example.bookingapp.domain.entities.Place
import com.example.bookingapp.domain.usecases.place.GetPlacesAndFeaturesByCompanyNameUseCase
import com.google.android.material.chip.Chip
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    getPlacesAndFeaturesByCompanyNameUseCase: GetPlacesAndFeaturesByCompanyNameUseCase,
    stateHandle: SavedStateHandle
) : ViewModel() {

    var booking: BookingBuilder = stateHandle[BOOKING]!!
    val placesAndFeatures = getPlacesAndFeaturesByCompanyNameUseCase(booking.companyName)
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList<Place>() to emptyList())

    fun setFilters(
        binding: FragmentPlacesBinding,
        filters: List<String>,
        listAdapter: PlaceListAdapter
    ) {
        with(binding) {
            for (filter in filters) {
                val chip = Chip(chipsFilter.context)
                chip.text = filter

                // Не уверен, что правильно реализовал фильтрацию, возможно можно подругому сделать
                chip.setOnClickListener {
                    if (chip.isSelected) {
                        chip.isSelected = false
                        listAdapter.removeFilter(chip.text.toString())
                    } else {
                        chip.isSelected = true
                        listAdapter.addFilter(chip.text.toString())
                    }
                    listAdapter.filterPlaces()
                }
                chipsFilter.addView(chip)
            }
        }
    }
}