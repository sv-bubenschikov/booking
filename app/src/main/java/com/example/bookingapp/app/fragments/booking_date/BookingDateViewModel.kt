package com.example.bookingapp.app.fragments.booking_date

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.app.entities.PeriodForFragment
import com.example.bookingapp.app.fragments.booking_date.BookingDateFragment.Companion.PLACE_ID
import com.example.bookingapp.domain.entities.Day
import com.example.bookingapp.domain.usecases.date.GetDaysInfoByPlaceIdUseCase
import com.example.bookingapp.domain.usecases.date.GetPeriodsByDayIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import javax.inject.Inject

@HiltViewModel
class BookingDateViewModel @Inject constructor(
    getDaysInfoByPlaceId: GetDaysInfoByPlaceIdUseCase,
    getPeriodsByDayId: GetPeriodsByDayIdUseCase,
    stateHandle: SavedStateHandle
) : ViewModel() {

    private val selectedDay = MutableSharedFlow<Int>()

    val periods = selectedDay.flatMapLatest { dayId ->
        getPeriodsByDayId(dayId)
    }.map { periods ->
        periods.map { period ->
            PeriodForFragment(
                timeStart = DateTime(period.timeStart).withZone(DateTimeZone.forID("Asia/Yekaterinburg")),
                timeEnd = DateTime(period.timeEnd).withZone(DateTimeZone.forID("Asia/Yekaterinburg")),
            )
        }
    }
        .flowOn(Dispatchers.Default)
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val days: StateFlow<List<Day>> = getDaysInfoByPlaceId()

    init {
        viewModelScope.launch {
            days.collect { dayList ->
                if (dayList.isNotEmpty())
                    onDayClicked(dayList.first().id)
            }
        }
    }

    fun onDayClicked(dayId: Int) {
        viewModelScope.launch {
            selectedDay.emit(dayId)
        }
    }
}