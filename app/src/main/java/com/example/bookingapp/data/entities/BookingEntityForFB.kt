package com.example.bookingapp.data.entities

data class BookingEntityForFB(
    val userId: String,
    val bookingName: String,
    val placeId: String,
    val bookingDate: Long,
    val startTime: Long,
    val endTime: Long,
    val id: String = "",
    )