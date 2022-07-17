package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.Day
import com.example.bookingapp.domain.entities.Period
import com.example.bookingapp.domain.repositories_interface.DateRepository
import org.joda.time.DateTime
import javax.inject.Inject

class DateRepositoryImpl @Inject constructor() : DateRepository {

    private val fakeDays = listOf(
        Day(1, 1, DateTime.now().millis),
        Day(2, 1, DateTime.now().millis),
        Day(3, 1, DateTime.now().millis),
        Day(4, 1, DateTime.now().millis),
        Day(5, 1, DateTime.now().millis),
        Day(6, 1, DateTime.now().millis),
        Day(7, 1, DateTime.now().millis),
        Day(8, 1, DateTime.now().millis),
        Day(9, 1, DateTime.now().millis),
    )


    private val fakePeriods = listOf(
        Period(1, 1, DateTime.now().millis, DateTime.now().millis, false),
        Period(2, 1, DateTime.now().millis, DateTime.now().millis, false),
        Period(3, 1, DateTime.now().millis, DateTime.now().millis, false),
        Period(4, 2, DateTime.now().millis, DateTime.now().millis, false),
        Period(5, 2, DateTime.now().millis, DateTime.now().millis, false),
    )


    override fun getDaysInfoByPlaceId(placeId: Int): List<Day> {
        return fakeDays.filter { day -> day.placeId == placeId }
    }

    override fun getPeriodsByDayId(dayId: Int): List<Period> {
        return fakePeriods.filter { period -> period.dayId == dayId }
    }
}