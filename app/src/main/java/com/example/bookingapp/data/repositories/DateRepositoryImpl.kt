package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.Day
import com.example.bookingapp.domain.entities.Period
import com.example.bookingapp.domain.repositories_interface.DateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.joda.time.DateTime
import javax.inject.Inject

class DateRepositoryImpl @Inject constructor() : DateRepository {

    private val today = DateTime.now().withTimeAtStartOfDay()

    private val fakeDays = listOf(
        Day(0, today.millis),
        Day(1, today.plusDays(1).millis),
        Day(2, today.plusDays(2).millis),
        Day(3, today.plusDays(3).millis),
        Day(4, today.plusDays(4).millis),
        Day(5, today.plusDays(5).millis),
        Day(6, today.plusDays(6).millis),
        Day(7, today.plusDays(7).millis),
        Day(8, today.plusDays(8).millis),
    )

    private val fakePeriods = listOf(
        Period(7200000, 9000000),
        Period(32400000, 34200000),
        Period(54000000, 55800000),
        Period(32400000, 34200000),
        Period(54000000, 55800000),
        Period(9000000, 10800000),
        Period(10800000, 12600000),
    )

    override fun getDaysInfoByPlaceId(): StateFlow<List<Day>> {
        return MutableStateFlow(fakeDays)
    }

    override fun getPeriodsByDayId(dayId: Int): StateFlow<List<Period>> {
        return MutableStateFlow(fakePeriods)
    }
}