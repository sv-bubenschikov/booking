package com.example.bookingapp.domain.entities

data class Booking(
    val userId: String,
    val bookingName: String,
    val placeId: String,
    val bookingDate: Long,
    val startTime: Long,
    val endTime: Long,
    var id: String = "",
)