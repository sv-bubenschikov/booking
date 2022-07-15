package com.example.bookingapp.domain.usecases.place

import com.example.bookingapp.domain.entities.Place
import com.example.bookingapp.domain.repositories_interface.PlaceRepository
import javax.inject.Inject

class GetPlacesInfoByCompanyIdUseCase @Inject constructor(private val placeRepository: PlaceRepository) {
    operator fun invoke(companyId: Int): List<Place> {
        return placeRepository.getPlacesInfoByCompanyId(companyId)
    }
}