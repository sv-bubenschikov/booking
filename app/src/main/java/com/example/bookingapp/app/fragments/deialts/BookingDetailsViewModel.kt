package com.example.bookingapp.app.fragments.deialts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.app.fragments.deialts.BookingDetailsFragment.Companion.BOOKING_ID
import com.example.bookingapp.domain.entities.Booking
import com.example.bookingapp.domain.usecases.booking.CreateBookingUseCase
import com.example.bookingapp.domain.usecases.booking.DeleteBookingByIdUseCase
import com.example.bookingapp.domain.usecases.booking.GetBookingInfoByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingDetailsViewModel @Inject constructor(
    getBookingInfoByIdUseCase: GetBookingInfoByIdUseCase,
    private val deleteBookingByIdUseCase: DeleteBookingByIdUseCase,
    private val createBookingUseCase: CreateBookingUseCase,
    stateHandle: SavedStateHandle
) : ViewModel() {

    val booking: StateFlow<Booking> = getBookingInfoByIdUseCase(stateHandle[BOOKING_ID]!!)

    fun onCancelBookingClicked(): Job {
        return viewModelScope.launch {
            deleteBookingByIdUseCase(booking.value.id)
        }
    }

    fun onConfirmBookingClicked(): Job {
        return viewModelScope.launch {
            createBookingUseCase(booking.value)
        }
    }
}