package com.example.bookingapp.data.dto

import com.example.bookingapp.data.entities.UserEntityForFB
import com.example.bookingapp.domain.entities.User
import com.google.firebase.auth.FirebaseAuth

fun User.toFB(): UserEntityForFB =
    UserEntityForFB(
        id = this.id,
        name = this.name,
        email = this.email,
    )

fun UserEntityForFB.toDomain(): User =
    User(
        id = this.id,
        name = this.name,
        email = this.email,
    )