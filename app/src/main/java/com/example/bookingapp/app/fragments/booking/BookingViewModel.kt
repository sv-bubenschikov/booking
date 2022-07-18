package com.example.bookingapp.app.fragments.booking

import androidx.lifecycle.ViewModel
import com.example.bookingapp.domain.usecases.booking.CreateBookingUseCase
import com.example.bookingapp.domain.usecases.booking.GetBookingsInfoByUserIdUseCase
import com.example.bookingapp.domain.usecases.user.GetCurrentUserUseCase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val createBookingUseCase: CreateBookingUseCase,
    private val getBookingsInfoByUserIdUseCase: GetBookingsInfoByUserIdUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {
    fun getCurrentUser(): FirebaseUser? {
        return getCurrentUserUseCase()
    }
}