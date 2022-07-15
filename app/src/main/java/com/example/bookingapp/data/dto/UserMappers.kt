package com.example.bookingapp.data.dto

import com.example.bookingapp.data.entities.UserEntityForFB
import com.example.bookingapp.domain.entities.User

fun User.toFB(): UserEntityForFB =
    UserEntityForFB(
        id = this.id,
        name = this.name,
        surname = this.surname,
        patronymic = this.patronymic,
        email = this.email,
        password = this.password
    )

fun UserEntityForFB.toDomain(): User =
    User(
        id = this.id,
        name = this.name,
        surname = this.surname,
        patronymic = this.patronymic,
        email = this.email,
        password = this.password
    )