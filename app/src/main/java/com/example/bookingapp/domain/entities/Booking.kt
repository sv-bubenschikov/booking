package com.example.bookingapp.domain.entities

data class Booking(
    val id: String = "",
    val startTime: Long = 0L,
    val endTime: Long = 0L,
    val companyName: String = "",
    val companyId: String = "",
    val placeName: String = "",
    val theme: String = "",
    val bookingDate: Long = 0L,
    val members: List<String> = emptyList(),
)