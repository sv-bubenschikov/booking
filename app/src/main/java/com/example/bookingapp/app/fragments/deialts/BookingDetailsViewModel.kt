package com.example.bookingapp.app.fragments.deialts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.bookingapp.app.fragments.deialts.BookingDetailsFragment.Companion.BOOKING_ID
import com.example.bookingapp.domain.entities.Booking
import com.example.bookingapp.domain.usecases.booking.GetBookingInfoByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BookingDetailsViewModel @Inject constructor(
    getBookingInfoByIdUseCase: GetBookingInfoByIdUseCase,
    stateHandle: SavedStateHandle
) : ViewModel() {

    val booking: StateFlow<Booking> = getBookingInfoByIdUseCase(stateHandle[BOOKING_ID]!!)
}