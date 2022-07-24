package com.example.bookingapp.app.fragments.deialts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.app.fragments.deialts.BookingDetailsFragment.Companion.BOOKING_ID
import com.example.bookingapp.domain.entities.Booking
import com.example.bookingapp.domain.usecases.booking.GetBookingInfoByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class BookingDetailsViewModel @Inject constructor(
    getBookingInfoByIdUseCase: GetBookingInfoByIdUseCase,
    stateHandle: SavedStateHandle
) : ViewModel() {

    val booking = getBookingInfoByIdUseCase(stateHandle[BOOKING_ID]!!)
        .stateIn(viewModelScope, SharingStarted.Eagerly, Booking())

    val bookingTime = booking.map { booking ->
        val dateTime = DateTime(booking.bookingDate).toLocalDate().toString("dd-MM-yyyy")
        val startTime = DateTime(booking.startTime).toLocalTime().toString("HH:mm")
        val endTime = DateTime(booking.endTime).toLocalTime().toString("HH:mm")
        "$dateTime; $startTime - $endTime"
    }
        .flowOn(Dispatchers.Default)
        .stateIn(viewModelScope, SharingStarted.Eagerly, "")
}