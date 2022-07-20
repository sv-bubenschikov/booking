package com.example.bookingapp.data.entities

import com.example.bookingapp.domain.entities.Period

data class PlaceEntityForFB(
    val name: String,
    val type: String,
    val floor: Int,
    val companyId: String,
    val numberParticipants: Int,
    val periods: List<Period>,
    val id: String ="",
)