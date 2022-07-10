package com.example.bookingapp.domain.usecases.place

import com.example.bookingapp.domain.entities.Place
import com.example.bookingapp.domain.repositories_interface.PlaceRepository

class GetPlaceInfoByIdUseCase(private val placeRepository: PlaceRepository) {
    operator fun invoke(id: Int): Place {
        return placeRepository.getPlaceInfoById(id)
    }
}