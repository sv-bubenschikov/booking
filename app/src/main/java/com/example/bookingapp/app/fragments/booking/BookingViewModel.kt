package com.example.bookingapp.app.fragments.booking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.domain.usecases.booking.GetBookingsByUserIdUseCase
import com.example.bookingapp.domain.usecases.user.CheckIfUserExistsUseCase
import com.example.bookingapp.domain.usecases.user.GetCurrentUserRefUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    getBookingsByUserIdUseCase: GetBookingsByUserIdUseCase,
    private val checkIfUserExistsUseCase: CheckIfUserExistsUseCase,
) : ViewModel() {

    val bookingList = getBookingsByUserIdUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _currentUserExists = MutableStateFlow(true)

    val currentUserExists: StateFlow<Boolean> = _currentUserExists

    fun updateCurrentUser() {
        viewModelScope.launch {
            _currentUserExists.value = checkIfUserExistsUseCase()
        }
    }
}