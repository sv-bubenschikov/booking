package com.example.bookingapp.data.entities

data class UserEntityForFB(
    val id: Int,
    val name: String,
    val surname: String,
    val patronymic: String,
    val email: String,
    val password: String
)