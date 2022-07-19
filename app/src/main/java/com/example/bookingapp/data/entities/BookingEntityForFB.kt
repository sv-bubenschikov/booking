package com.example.bookingapp.data.entities


data class BookingEntityForFB(
    val id: Int,
    val userId: Int,
    val company: CompanyEntityForFB,
    val place: PlaceEntityForFB,
    val bookingDate: BookingDateEntityForFB,
    val bookingName: String
)