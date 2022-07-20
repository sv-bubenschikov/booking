package com.example.bookingapp.data.entities

data class PlaceEntityForFB(
    val id: Int,
    val companyId: Int,
    val name: String,
    val type: String,
    val floor: String,
    val numberParticipants: Int,
    val attributes: List<String>
)