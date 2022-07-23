package com.example.bookingapp.domain.entities

data class Booking(
    val startTime: Long = 0L,
    val endTime: Long = 0L,
    val company: String = "",
    val place: String = "",
    val theme: String = "",
    val bookingDate: Long = 0L,
    val members: List<String> = emptyList(),
    val placeId: String = "",
) {
    var id = ""
}