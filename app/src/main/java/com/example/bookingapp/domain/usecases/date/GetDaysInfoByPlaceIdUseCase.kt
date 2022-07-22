package com.example.bookingapp.domain.usecases.date

import com.example.bookingapp.domain.entities.Day
import com.example.bookingapp.domain.repositories_interface.DateRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetDaysInfoByPlaceIdUseCase @Inject constructor(private val dateRepository: DateRepository) {
    operator fun invoke(): StateFlow<List<Day>> {
        return dateRepository.getDaysInfoByPlaceId()
    }
}