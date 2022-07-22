package com.example.bookingapp.app.fragments.places

import androidx.lifecycle.ViewModel
import com.example.bookingapp.domain.entities.Place
import com.example.bookingapp.domain.usecases.place.GetPlacesInfoByCompanyIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val getPlacesInfoByCompanyIdUseCase: GetPlacesInfoByCompanyIdUseCase
) : ViewModel() {

    fun getPlacesInfo(companyId: Int): List<Place> {
        return getPlacesInfoByCompanyIdUseCase(companyId)
    }
}