package com.example.bookingapp.domain.usecases.date

import com.example.bookingapp.domain.repositories_interface.DateRepository
import javax.inject.Inject

class GetPeriodsByDayIdUseCase @Inject constructor(
    private val dateRepository: DateRepository
) {

    operator fun invoke(dayId: Int, place: String) =
        dateRepository.getPeriodsByDayId(dayId, place)
}