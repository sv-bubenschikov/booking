package com.example.bookingapp.domain.entities

data class Booking(
    val id: Int,
    val userId: Int,
    val company: Company,
    val place: Place,
    val bookingDate: BookingDate
)