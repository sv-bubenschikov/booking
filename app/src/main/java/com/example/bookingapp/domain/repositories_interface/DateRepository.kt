package com.example.bookingapp.domain.repositories_interface

import com.example.bookingapp.domain.entities.Day
import com.example.bookingapp.domain.entities.Period

interface DateRepository {

    fun getDaysInfoByPlaceId(placeId: Int): List<Day>

    fun getPeriodsByDayId(dayId: Int): List<Period>
}