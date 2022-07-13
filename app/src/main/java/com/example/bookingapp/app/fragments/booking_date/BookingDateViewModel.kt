package com.example.bookingapp.app.fragments.booking_date

import androidx.lifecycle.ViewModel
import com.example.bookingapp.domain.entities.DateWeek
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookingDateViewModel @Inject constructor() : ViewModel() {
    //fake data нуно определиться с классом, в  котором будет удобно хранить все данные
    private val fakeDates = listOf(
        DateWeek("1", "ПН"),
        DateWeek("2", "ВТ"),
        DateWeek("3", "СР"),
        DateWeek("4", "ЧТ"),
        DateWeek("5", "ПТ"),
        DateWeek("6", "СБ"),
        DateWeek("7", "ВС"),
        DateWeek("8", "ПН"),
        DateWeek("9", "ВТ"),
        DateWeek("10", "СР"),
        DateWeek("11", "ЧТ"),
        DateWeek("12", "ПТ"),
        DateWeek("13", "СБ"),
        DateWeek("14", "ВС")
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
    fun getDates(): List<DateWeek> {
        return fakeDates
    }

    fun getDateMorning(): List<String> {
        return fakeDate//filter по времени
    }

}