package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.Day
import com.example.bookingapp.domain.entities.Period
import com.example.bookingapp.domain.repositories_interface.DateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
        Period(1, 1, 7200000, 9000000, false),
        Period(2, 1, 32400000, 34200000, false),
        Period(3, 1, 54000000, 55800000, false),
        Period(4, 2, 32400000, 34200000, false),
        Period(5, 2, 54000000, 55800000, false),
        Period(6, 1, 9000000, 10800000, false),
        Period(7, 1, 10800000, 12600000, false),
    )

    override fun getDaysInfoByPlaceId(placeId: Int): StateFlow<List<Day>> {
        return MutableStateFlow(fakeDays.filter { day -> day.placeId == placeId })
    }

    override fun getPeriodsByDayId(dayId: Int): StateFlow<List<Period>> {
        return MutableStateFlow(fakePeriods.filter { period -> period.dayId == dayId })
    }
}