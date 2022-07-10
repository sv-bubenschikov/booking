package com.example.bookingapp.domain.entities

data class Place(
    val id: Int,
    val name: String,
    val type: String,
    val floor: Int,
    val numberParticipants: Int,
    val attributes: List<String>
)