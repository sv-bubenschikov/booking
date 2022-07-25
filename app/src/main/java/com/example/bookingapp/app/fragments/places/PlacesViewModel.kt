package com.example.bookingapp.app.fragments.places

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.app.fragments.places.PlacesFragment.Companion.BOOKING
import com.example.bookingapp.domain.entities.BookingBuilder
import com.example.bookingapp.domain.entities.Place
import com.example.bookingapp.domain.usecases.place.GetPlacesByCompanyNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    getPlacesByCompanyNameUseCase: GetPlacesByCompanyNameUseCase,
    stateHandle: SavedStateHandle
) : ViewModel() {

    var booking: BookingBuilder = stateHandle[BOOKING]!!

    private val _selectedFeatures = MutableStateFlow(emptyList<String>())

    private val _places = getPlacesByCompanyNameUseCase(booking.companyName)
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _features = _places.map { places ->
        places.flatMap { place -> place.features }.toSet().toList()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val places: StateFlow<List<Place>> = _selectedFeatures
        .flatMapLatest { filters ->
            _places.map { places ->
                places.filter { it.features.containsAll(filters) }
            }
        }
        .flowOn(Dispatchers.Default)
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val features: StateFlow<List<String>> = _features

    val selectedFeatures: StateFlow<List<String>> = _selectedFeatures

    fun onFeatureClicked(feature: String, isSelected: Boolean) {
        viewModelScope.launch(Dispatchers.Default) {
            _selectedFeatures.value = if (isSelected)
                _selectedFeatures.value + listOf(feature)
            else
                _selectedFeatures.value.filterNot { it == feature }
        }
    }
}