package com.example.bookingapp.domain.usecases.date

import com.example.bookingapp.domain.entities.Period
import com.example.bookingapp.domain.repositories_interface.DateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPeriodsByDayIdUseCase @Inject constructor(private val dateRepository: DateRepository) {
    operator fun invoke(dayId: Int, place: String): Flow<List<Period>> {
        return dateRepository.getPeriodsByDayId(dayId, place)
    }
}