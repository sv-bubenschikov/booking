package com.example.bookingapp.app.fragments.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.domain.usecases.booking.GetBookingsByUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    getBookingsByUserIdUseCase: GetBookingsByUserIdUseCase
) : ViewModel() {

    val bookingList = getBookingsByUserIdUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}