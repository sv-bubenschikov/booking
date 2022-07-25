package com.example.bookingapp.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookingBuilder(
    var companyName: String = "",
    var placeId: String = "",
    var placeName: String = "",
    var placeType: String = "",
    var theme: String = "",
    var bookingDate: Long = 0L,
    var startTime: Long = 0L,
    var endTime: Long = 0L,
    var members: List<String> = emptyList(),
): Parcelable