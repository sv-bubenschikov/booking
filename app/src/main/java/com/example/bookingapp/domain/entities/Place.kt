package com.example.bookingapp.domain.entities

data class Place(
    val company: String = "",
    val features: List<String> = emptyList(),
    val name: String = "",
    val type: String = "",
    val periods: List<Period> = emptyList(),
) {
    var id = ""
}