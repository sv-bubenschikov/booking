package com.example.bookingapp.domain.entities

data class Place(
    val name: String,
    val type: String,
    val floor: Int,
    val numberParticipants: Int,
    val companyId: String,
    val periods: List<Period>,
    var id: String = ""
)