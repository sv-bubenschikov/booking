package com.example.bookingapp.app.fragments.booking_date

import androidx.lifecycle.ViewModel
import com.example.bookingapp.domain.entities.DayBooking
import dagger.hilt.android.lifecycle.HiltViewModel
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class BookingDateViewModel @Inject constructor() : ViewModel() {
    //fake data нуно определиться с классом, в  котором будет удобно хранить все данные
    private val fakeDates = listOf(
        DayBooking(1, 1,DateTime.now().millis),
        DayBooking(2, 1,DateTime.now().millis),
        DayBooking(3, 1,DateTime.now().millis),
        DayBooking(4, 1,DateTime.now().millis),
        DayBooking(5, 1,DateTime.now().millis),
        DayBooking(6, 1,DateTime.now().millis),
        DayBooking(7, 1,DateTime.now().millis),
        DayBooking(8, 1,DateTime.now().millis),
        DayBooking(9, 1,DateTime.now().millis),
    )

    private val fakeDate = listOf(
        "8:00 - 8:30",
        "8:30 - 9:00",
        "9:00 - 9:30",
        "9:30 - 10:00",
        "10:00 - 10:30",
        "10:30 - 11:00",
        "11:00 - 11:30",
        "11:30 - 12:00",
    )

    //пока возвращает значение
    fun getDates(): List<DayBooking> {
        return fakeDates
    }

    fun getDateMorning(): List<String> {
        return fakeDate//filter по времени
    }

}