package com.example.bookingapp.domain.repositories_interface

import com.example.bookingapp.domain.entities.Day
import com.example.bookingapp.domain.entities.Period
import kotlinx.coroutines.flow.Flow

interface DateRepository {

    fun getDaysInfoByPlaceId(): Flow<List<Day>>

    fun getPeriodsByDayId(dayId: Int, placeId: String): Flow<List<Period>>

    fun getBookingPeriodsByDate(date: Long, placeId: String): Flow<List<Period>>
}