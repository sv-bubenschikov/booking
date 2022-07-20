package com.example.bookingapp.domain.entities

data class Place(
    val id: Int,
    val companyId: Int,
    val name: String,
    val type: String,
    val floor: String,
    val numberParticipants: Int,
    val attributes: List<String>
)