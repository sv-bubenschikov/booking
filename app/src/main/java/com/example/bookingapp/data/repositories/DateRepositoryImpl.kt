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
        Day(1,  DateTime.now().millis),
        Day(2,  DateTime.now().millis),
        Day(3,  DateTime.now().millis),
        Day(4,  DateTime.now().millis),
        Day(5,  DateTime.now().millis),
        Day(6,  DateTime.now().millis),
        Day(7,  DateTime.now().millis),
        Day(8,  DateTime.now().millis),
    )

    private val fakePeriods = listOf(
        Period(7200000, 9000000),
        Period( 32400000, 34200000),
        Period( 54000000, 55800000),
        Period( 32400000, 34200000),
        Period( 54000000, 55800000),
        Period( 9000000, 10800000),
        Period( 10800000, 12600000),
    )

    override fun getDaysInfoByPlaceId(): StateFlow<List<Day>> {
        return MutableStateFlow(fakeDays)
    }

    override fun getPeriodsByDayId(dayId: Int): StateFlow<List<Period>> {
        return MutableStateFlow(fakePeriods)
    }
}