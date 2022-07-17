package com.example.bookingapp.domain.usecases.date

import com.example.bookingapp.domain.entities.Day
import com.example.bookingapp.domain.repositories_interface.DateRepository
import javax.inject.Inject

class GetDaysInfoByPlaceIdUseCase @Inject constructor(private val dateRepository: DateRepository) {
    operator fun invoke(placeId: Int): List<Day> {
        return dateRepository.getDaysInfoByPlaceId(placeId)
    }
}