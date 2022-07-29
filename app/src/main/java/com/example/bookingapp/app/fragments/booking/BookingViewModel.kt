package com.example.bookingapp.app.fragments.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.domain.entities.Booking
import com.example.bookingapp.domain.usecases.booking.GetBookingsByUserIdAsyncUseCase
import com.example.bookingapp.domain.usecases.booking.GetBookingsByUserIdUseCase
import com.example.bookingapp.domain.usecases.user.GetCurrentUserRefUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    getBookingsByUserIdUseCase: GetBookingsByUserIdUseCase,
    getCurrentUserRefUseCase: GetCurrentUserRefUseCase,
    private val getBookingsByUserIdAsyncUseCase: GetBookingsByUserIdAsyncUseCase,
) : ViewModel() {

    private fun getBookings() {
        viewModelScope.launch {
            _bookingList.value = getBookingsByUserIdAsyncUseCase()
            _isLoaded.value = true
        }
    }

    init {
        getBookings()
        viewModelScope.launch {
            realTimeBookingList.collect {
                _bookingList.value = it
            }
        }

    }

    private val _isLoaded = MutableStateFlow(false)
    val isLoaded: StateFlow<Boolean> = _isLoaded

    private val _bookingList = MutableStateFlow<List<Booking>>(emptyList())
    val bookingList: StateFlow<List<Booking>> = _bookingList

    val currentUserRef= getCurrentUserRefUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val realTimeBookingList = getBookingsByUserIdUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}