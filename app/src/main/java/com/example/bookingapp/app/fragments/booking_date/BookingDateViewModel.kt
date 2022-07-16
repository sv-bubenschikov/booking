package com.example.bookingapp.app.fragments.booking_date

import androidx.lifecycle.ViewModel
import com.example.bookingapp.domain.entities.DayBooking
import com.example.bookingapp.domain.entities.Period
import dagger.hilt.android.lifecycle.HiltViewModel
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class BookingDateViewModel @Inject constructor() : ViewModel() {
    //fake data нуно определиться с классом, в  котором будет удобно хранить все данные
    private val fakeDates = listOf(
        DayBooking(1, 1, DateTime.now().millis),
        DayBooking(2, 1, DateTime.now().millis),
        DayBooking(3, 1, DateTime.now().millis),
        DayBooking(4, 1, DateTime.now().millis),
        DayBooking(5, 1, DateTime.now().millis),
        DayBooking(6, 1, DateTime.now().millis),
        DayBooking(7, 1, DateTime.now().millis),
        DayBooking(8, 1, DateTime.now().millis),
        DayBooking(9, 1, DateTime.now().millis),
    )


    private val fakePeriod = listOf(
        Period(1, 1, DateTime.now().millis, DateTime.now().millis, false),
        Period(2, 1, DateTime.now().millis, DateTime.now().millis, false),
        Period(3, 1, DateTime.now().millis, DateTime.now().millis, false),
        Period(4, 1, DateTime.now().millis, DateTime.now().millis, false),
        Period(5, 1, DateTime.now().millis, DateTime.now().millis, false),
    )

    //пока возвращает значение
    fun getDates(): List<DayBooking> {
        return fakeDates
    }

    fun getDateMorning(): List<Period> {
        return fakePeriod//filter по времени
    }

}