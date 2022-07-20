package com.example.bookingapp.domain.usecases.place

import com.example.bookingapp.domain.entities.Place
import com.example.bookingapp.domain.repositories_interface.PlaceRepository
import javax.inject.Inject

class DeletePlaceByIdUseCase @Inject constructor (private val placeRepository: PlaceRepository) {
    suspend operator fun invoke(id: String) =
        placeRepository.deletePlaceById(id)
}