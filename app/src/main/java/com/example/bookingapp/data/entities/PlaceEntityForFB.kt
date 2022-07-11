package com.example.bookingapp.data.entities

data class PlaceEntityForFB(
    val id: Int,
    val name: String,
    val type: String,
    val floor: Int,
    val numberParticipants: Int,
    val attributes: List<String>
)