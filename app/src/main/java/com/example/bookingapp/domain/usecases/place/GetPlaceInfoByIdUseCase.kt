package com.example.bookingapp.domain.usecases.place

import com.example.bookingapp.domain.repositories_interface.PlaceRepository
import javax.inject.Inject

class GetPlaceInfoByIdUseCase @Inject constructor(private val placeRepository: PlaceRepository) {
    operator fun invoke(id: String) =
        placeRepository.getPlaceInfoById(id)
}