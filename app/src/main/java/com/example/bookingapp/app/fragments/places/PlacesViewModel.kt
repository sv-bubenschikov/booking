package com.example.bookingapp.app.fragments.places

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.bookingapp.app.fragments.places.PlacesFragment.Companion.BOOKING
import com.example.bookingapp.domain.entities.BookingBuilder
import com.example.bookingapp.domain.usecases.place.GetPlacesAndFeaturesByCompanyNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    getPlacesAndFeaturesByCompanyNameUseCase: GetPlacesAndFeaturesByCompanyNameUseCase,
    stateHandle: SavedStateHandle
) : ViewModel() {

    var booking: BookingBuilder = stateHandle[BOOKING]!!
    val placesAndFeatures = getPlacesAndFeaturesByCompanyNameUseCase(booking.companyName)
}