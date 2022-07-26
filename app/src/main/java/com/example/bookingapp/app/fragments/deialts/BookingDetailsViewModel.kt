package com.example.bookingapp.app.fragments.deialts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.app.fragments.deialts.BookingDetailsFragment.Companion.BOOKING
import com.example.bookingapp.app.fragments.deialts.BookingDetailsFragment.Companion.BOOKING_ID
import com.example.bookingapp.domain.entities.Booking
import com.example.bookingapp.domain.entities.BookingBuilder
import com.example.bookingapp.domain.usecases.booking.CreateBookingUseCase
import com.example.bookingapp.domain.usecases.booking.DeleteBookingByIdUseCase
import com.example.bookingapp.domain.usecases.booking.GetBookingInfoByIdUseCase
import com.example.bookingapp.domain.usecases.user.GetCurrentUserRefUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class BookingDetailsViewModel @Inject constructor(
    getBookingInfoByIdUseCase: GetBookingInfoByIdUseCase,
    private val deleteBookingByIdUseCase: DeleteBookingByIdUseCase,
    private val createBookingUseCase: CreateBookingUseCase,
    private val userRefUseCase: GetCurrentUserRefUseCase,
    stateHandle: SavedStateHandle
) : ViewModel() {

    private val _bookingDecision = MutableSharedFlow<BookingDecision>()
    private val _bookingTheme = MutableStateFlow("")
    private val bookingId: String? = stateHandle[BOOKING_ID]

    val bookingDecision: Flow<BookingDecision> = _bookingDecision
    val bookingTheme: Flow<String> = _bookingTheme

    val booking = if (bookingId == null) {
        flow {
            val builder: BookingBuilder = stateHandle[BOOKING]!!
            emit(builder.build())
        }
    } else {
        getBookingInfoByIdUseCase(bookingId)
    }
        .stateIn(viewModelScope, SharingStarted.Eagerly, Booking())

    val isFromDateFragment = bookingId == null

    val bookingTime = booking.map { booking ->
        val dateTime = DateTime(booking.bookingDate).toLocalDate().toString("dd-MM-yyyy")
        val startTime = DateTime(booking.startTime).toLocalTime().toString("HH:mm")
        val endTime = DateTime(booking.endTime).toLocalTime().toString("HH:mm")
        "$dateTime; $startTime - $endTime"
    }
        .flowOn(Dispatchers.Default)
        .stateIn(viewModelScope, SharingStarted.Eagerly, "")

    fun onCancelBookingClicked() {
        viewModelScope.launch {
            deleteBookingByIdUseCase(booking.value.id)
            _bookingDecision.emit(BookingDecision.CANCEL)
        }
    }

    fun onConfirmBookingClicked() {
        viewModelScope.launch {
            createBookingUseCase(booking.value.copy(
                theme = _bookingTheme.value,
                members = listOf(userRefUseCase()!!.uid)
            ))
            _bookingDecision.emit(BookingDecision.CONFIRM)
        }
    }

    fun onBookingThemeInputChanged(bookingTheme: String) {
        _bookingTheme.value = bookingTheme
    }
}