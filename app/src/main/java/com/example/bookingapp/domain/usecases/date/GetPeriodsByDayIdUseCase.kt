package com.example.bookingapp.domain.usecases.date

import com.example.bookingapp.domain.entities.Period
import com.example.bookingapp.domain.repositories_interface.DateRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetPeriodsByDayIdUseCase @Inject constructor(private val dateRepository: DateRepository) {
    operator fun invoke(dayId: Int): StateFlow<List<Period>> {
        return dateRepository.getPeriodsByDayId(dayId)
    }
}