package com.example.bookingapp.app.entities

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

data class PeriodForFragment(
    val id: Int,
    val timeStart: DateTime,
    val timeEnd: DateTime,
    val statusBooking: Boolean
) {
    override fun toString() = DateTimeFormat.forPattern("HH:mm").print(timeStart) +
            " - " + DateTimeFormat.forPattern("HH:mm").print(timeEnd)

}
