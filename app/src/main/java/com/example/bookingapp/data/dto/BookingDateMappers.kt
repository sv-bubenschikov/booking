package com.example.bookingapp.data.dto

import com.example.bookingapp.data.entities.BookingDateEntityForFB
import com.example.bookingapp.domain.entities.BookingDate

fun BookingDate.toFB(): BookingDateEntityForFB =
    BookingDateEntityForFB()

fun BookingDateEntityForFB.toDomain(): BookingDate =
    BookingDate()