package com.example.bookingapp.domain.usecases.place

import com.example.bookingapp.domain.repositories_interface.PlaceRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPlacesByCompanyNameUseCase @Inject constructor(
    private val placeRepository: PlaceRepository
) {

    operator fun invoke(companyId: String) = placeRepository
        .getPlacesInfoByCompanyName(companyId)
}