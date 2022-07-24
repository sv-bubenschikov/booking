package com.example.bookingapp.app.fragments.deialts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.app.fragments.deialts.BookingDetailsFragment.Companion.BOOKING_ID
import com.example.bookingapp.app.fragments.deialts.BookingDetailsFragment.Companion.IS_FROM_DATE_FRAGMENT
import com.example.bookingapp.domain.entities.Booking
import com.example.bookingapp.domain.usecases.booking.CreateBookingUseCase
import com.example.bookingapp.domain.usecases.booking.DeleteBookingByIdUseCase
import com.example.bookingapp.domain.usecases.booking.GetBookingInfoByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.joda.time.DateTime
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingDetailsViewModel @Inject constructor(
    getBookingInfoByIdUseCase: GetBookingInfoByIdUseCase,
    private val deleteBookingByIdUseCase: DeleteBookingByIdUseCase,
    private val createBookingUseCase: CreateBookingUseCase,
    stateHandle: SavedStateHandle
) : ViewModel() {

    private val _bookingDecision = MutableSharedFlow<BookingDecision>()

    val bookingDecision: Flow<BookingDecision> = _bookingDecision

    val booking = getBookingInfoByIdUseCase(stateHandle[BOOKING_ID]!!)
        .stateIn(viewModelScope, SharingStarted.Eagerly, Booking())

    val isFromDateFragment: Boolean = stateHandle[IS_FROM_DATE_FRAGMENT]!!

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
            createBookingUseCase(booking.value)
            _bookingDecision.emit(BookingDecision.CONFIRM)
        }
    }

    fun onEditBookingClicked() {
        viewModelScope.launch {
            _bookingDecision.emit(BookingDecision.EDIT)
        }
    }
}