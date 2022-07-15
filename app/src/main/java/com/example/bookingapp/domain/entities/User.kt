package com.example.bookingapp.domain.entities

data class User(
    val id: Int,
    val name: String,
    val surname: String,
    val patronymic: String,
    val email: String,
    val password: String
)