package com.example.bookingapp.app.fragments.booking_date

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.R
import com.example.bookingapp.app.entities.PeriodForFragment
import com.example.bookingapp.app.fragments.booking_date.BookingDateFragment.Companion.BOOKING
import com.example.bookingapp.domain.entities.BookingBuilder
import com.example.bookingapp.domain.entities.Day
import com.example.bookingapp.domain.usecases.date.GetBookingPeriodsByDateUseCase
import com.example.bookingapp.domain.usecases.date.GetDaysInfoByPlaceIdUseCase
import com.example.bookingapp.domain.usecases.date.GetPeriodsByDayIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import javax.inject.Inject

@HiltViewModel
class BookingDateViewModel @Inject constructor(
    getDaysInfoByPlaceId: GetDaysInfoByPlaceIdUseCase,
    getPeriodsByDayId: GetPeriodsByDayIdUseCase,
    getBookingPeriodsByDate: GetBookingPeriodsByDateUseCase,
    stateHandle: SavedStateHandle
) : ViewModel() {

    val booking: BookingBuilder = stateHandle[BOOKING]!!
    private val placeId = booking.placeId

    private val _selectedDay =
        MutableStateFlow(Day(0, DateTime.now().withTimeAtStartOfDay().millis))
    private val _days = MutableStateFlow(emptyList<Day>())
    private val _selectedPeriod = MutableStateFlow<PeriodForFragment?>(null)
    private val _complete = MutableSharedFlow<BookingBuilder>()
    private val _errorMessage = MutableSharedFlow<Int>()

    val days: StateFlow<List<Day>> = _days
    val selectedPeriod: StateFlow<PeriodForFragment?> = _selectedPeriod
    val complete: Flow<BookingBuilder> = _complete
    val errorMessage: Flow<Int> = _errorMessage

    val periods: StateFlow<List<PeriodForFragment>> = _selectedDay.flatMapLatest { day ->
        val allPeriods = getPeriodsByDayId(day.id, placeId)
        val bookingPeriods = getBookingPeriodsByDate(days.first()[day.id].date, placeId)

        // TODO: заменить на flatmap
        allPeriods.map {
            it.filter { p ->
                !bookingPeriods.first().contains(p)
            }
        }
    }.map { periods ->
        periods.map { period ->
            PeriodForFragment(
                timeStart = DateTime(period.timeStart).withZone(DateTimeZone.forID("Asia/Yekaterinburg")),
                timeEnd = DateTime(period.timeEnd).withZone(DateTimeZone.forID("Asia/Yekaterinburg")),
            )
        }
    }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    init {
        viewModelScope.launch {
            getDaysInfoByPlaceId().collect { dayList ->
                _days.value = dayList
                onDayClicked(dayList.first())
            }
        }
    }

    fun onDayClicked(checkedDay: Day) {
        viewModelScope.launch {
            _days.value = days.value.run {
                map { day ->
                    if (day.isSelected && day != checkedDay) {
                        // убираем отметку с прошлого выбора
                        _selectedPeriod.value = null
                        day.copy(isSelected = false)
                    } else if (day == checkedDay)
                    // отмечаем новый элемент выбраным
                        day.copy(isSelected = true).also {
                            _selectedDay.emit(it)
                        }
                    else
                        day
                }
            }
        }

    }

    fun onPeriodClicked(period: PeriodForFragment) {
        viewModelScope.launch {
            _selectedPeriod.value = if (_selectedPeriod.value == period)
                null
            else
                period
        }
    }

    fun onCompleteClicked() {
        viewModelScope.launch {
            val period = selectedPeriod.value
            if (period == null)
                _errorMessage.emit(R.string.period_required)
            else
                _complete.emit(
                    booking.copy(
                        startTime = period.timeStart.millis,
                        endTime = period.timeEnd.millis,
                        bookingDate = _selectedDay.value.date
                    )
                )
        }
    }
}