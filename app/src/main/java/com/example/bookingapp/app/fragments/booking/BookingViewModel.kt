package com.example.bookingapp.app.fragments.booking

import androidx.lifecycle.ViewModel
import com.example.bookingapp.domain.usecases.booking.CreateBookingUseCase
import com.example.bookingapp.domain.usecases.booking.GetBookingsInfoByUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val createBookingUseCase: CreateBookingUseCase,
    private val getBookingsInfoByUserIdUseCase: GetBookingsInfoByUserIdUseCase
) : ViewModel() {
    // TODO: Implement the ViewModel
}