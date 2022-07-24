package com.example.bookingapp.app.fragments.places

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.app.fragments.places.PlacesFragment.Companion.COMPANY_ID
import com.example.bookingapp.domain.entities.Place
import com.example.bookingapp.domain.usecases.place.GetPlacesAndFeaturesByCompanyNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    getPlacesAndFeaturesByCompanyNameUseCase: GetPlacesAndFeaturesByCompanyNameUseCase,
    stateHandle: SavedStateHandle
) : ViewModel() {

    val placesAndFeatures = getPlacesAndFeaturesByCompanyNameUseCase(stateHandle[COMPANY_ID]!!)
}