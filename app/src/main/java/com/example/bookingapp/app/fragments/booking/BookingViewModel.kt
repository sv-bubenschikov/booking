package com.example.bookingapp.app.fragments.booking

import androidx.lifecycle.ViewModel
import com.example.bookingapp.domain.entities.Booking
import com.example.bookingapp.domain.usecases.booking.GetBookingsInfoByUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    getBookingsInfoByUserIdUseCase: GetBookingsInfoByUserIdUseCase
) : ViewModel() {

    // TODO: #22
    val bookingList: StateFlow<List<Booking>> = getBookingsInfoByUserIdUseCase(0)
}