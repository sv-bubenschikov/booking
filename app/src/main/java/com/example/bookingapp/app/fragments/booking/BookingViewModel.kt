package com.example.bookingapp.app.fragments.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.domain.usecases.booking.GetBookingsByUserIdUseCase
import com.example.bookingapp.domain.usecases.user.GetCurrentUserRefUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    getBookingsByUserIdUseCase: GetBookingsByUserIdUseCase,
    getCurrentUserRefUseCase: GetCurrentUserRefUseCase,
) : ViewModel() {

    val currentUserRef= getCurrentUserRefUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val bookingList = getBookingsByUserIdUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}