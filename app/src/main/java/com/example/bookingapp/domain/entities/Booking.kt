package com.example.bookingapp.domain.entities

data class Booking(
    var id: String = "",
    val startTime: Long = 0L,
    val endTime: Long = 0L,
    val bookingDate: Long = 0L,
    val companyName: String = "",
    val placeName: String = "",
    val placeId: String = "",
    val theme: String = "",
    val members: List<String> = emptyList()
)