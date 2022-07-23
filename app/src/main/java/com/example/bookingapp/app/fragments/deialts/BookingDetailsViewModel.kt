package com.example.bookingapp.app.fragments.deialts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.app.fragments.deialts.BookingDetailsFragment.Companion.BOOKING_ID
import com.example.bookingapp.domain.entities.Booking
import com.example.bookingapp.domain.usecases.booking.GetBookingInfoByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BookingDetailsViewModel @Inject constructor(
    private val getBookingInfoByIdUseCase: GetBookingInfoByIdUseCase,
    stateHandle: SavedStateHandle
) : ViewModel() {

    lateinit var booking: StateFlow<Booking>
    fun setBooking(id: String) {
        booking = getBookingInfoByIdUseCase(id).stateIn(viewModelScope, SharingStarted.Eagerly, Booking())
    }
}