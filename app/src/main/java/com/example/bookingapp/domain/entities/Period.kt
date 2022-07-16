package com.example.bookingapp.domain.entities

data class Period(
    val id: Int,
    val dayId: Int,
    val timeStart: Long,
    val timeEnd: Long,
    val statusBooking: Boolean
)
