package com.example.bookingapp.data.dto

import com.example.bookingapp.data.entities.BookingEntityForFB
import com.example.bookingapp.domain.entities.Booking

fun Booking.toFB(): BookingEntityForFB =
    BookingEntityForFB(
        id = this.id,
        userId = this.userId,
        bookingName = this.bookingName,
        placeId = this.placeId,
        bookingDate = this.bookingDate,
        startTime = this.startTime,
        endTime = this.endTime
    )

fun BookingEntityForFB.toDomain(): Booking =
    Booking(
        id = this.id,
        userId = this.userId,
        bookingName = this.bookingName,
        placeId = this.placeId,
        bookingDate = this.bookingDate,
        startTime = this.startTime,
        endTime = this.endTime
    )