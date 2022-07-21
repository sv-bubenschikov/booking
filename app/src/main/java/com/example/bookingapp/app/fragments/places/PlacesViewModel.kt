package com.example.bookingapp.app.fragments.places

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.bookingapp.app.fragments.places.PlacesFragment.Companion.COMPANY_ID
import com.example.bookingapp.domain.entities.Place
import com.example.bookingapp.domain.usecases.place.GetPlacesInfoByCompanyIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    getPlacesInfoByCompanyIdUseCase: GetPlacesInfoByCompanyIdUseCase,
    stateHandle: SavedStateHandle
) : ViewModel() {

    val places: StateFlow<List<Place>> =
        getPlacesInfoByCompanyIdUseCase(stateHandle[COMPANY_ID]!!)
}