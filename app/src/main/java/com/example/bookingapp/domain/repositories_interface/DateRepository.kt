package com.example.bookingapp.domain.repositories_interface

import com.example.bookingapp.domain.entities.Day
import com.example.bookingapp.domain.entities.Period
import kotlinx.coroutines.flow.StateFlow

interface DateRepository {

    fun getDaysInfoByPlaceId(): StateFlow<List<Day>>

    fun getPeriodsByDayId(dayId: Int): StateFlow<List<Period>>
}