package com.example.bookingapp.domain.usecases.date

import com.example.bookingapp.domain.repositories_interface.DateRepository
import javax.inject.Inject

class GetDaysInfoByPlaceIdUseCase @Inject constructor(
    private val dateRepository: DateRepository
) {

    operator fun invoke() =
        dateRepository.getDaysInfoByPlaceId()
}