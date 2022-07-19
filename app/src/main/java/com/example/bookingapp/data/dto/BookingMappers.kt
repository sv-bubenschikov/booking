package com.example.bookingapp.data.dto

import com.example.bookingapp.data.entities.BookingEntityForFB
import com.example.bookingapp.domain.entities.Booking

fun Booking.toFB(): BookingEntityForFB =
    BookingEntityForFB(
        id = this.id,
        userId = this.userId,
        company = this.company.toFB(),
        place = this.place.toFB(),
        bookingDate = this.bookingDate.toFB(),
        bookingName = this.bookingName
    )

fun BookingEntityForFB.toDomain(): Booking =
    Booking(
        id = this.id,
        userId = this.userId,
        company = this.company.toDomain(),
        place = this.place.toDomain(),
        bookingDate = this.bookingDate.toDomain(),
        bookingName = this.bookingName
    )