package com.example.bookingapp.app.fragments.booking_date

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.bookingapp.app.entities.PeriodForFragment
import com.example.bookingapp.app.fragments.booking_date.BookingDateFragment.Companion.PLACE_ID
import com.example.bookingapp.domain.entities.Day
import com.example.bookingapp.domain.usecases.date.GetDaysInfoByPlaceIdUseCase
import com.example.bookingapp.domain.usecases.date.GetPeriodsByDayIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import javax.inject.Inject

@HiltViewModel
class BookingDateViewModel @Inject constructor(
    private val getDaysInfoByPlaceId: GetDaysInfoByPlaceIdUseCase,
    private val getPeriodsByDayId: GetPeriodsByDayIdUseCase,
    stateHandle: SavedStateHandle
) : ViewModel() {

    val periods: MutableStateFlow<List<PeriodForFragment>> = MutableStateFlow(emptyList())

    val days: StateFlow<List<Day>> = getDaysInfoByPlaceId(stateHandle[PLACE_ID]!!).apply {
        if (this.value.isNotEmpty())
            getPeriods(this.value.first().id)
    }

    fun getPeriods(dayId: Int) {

        periods.value =
            getPeriodsByDayId(dayId).value.map { p ->
            PeriodForFragment(
                id = p.id,
                timeStart = DateTime(p.timeStart).withZone(DateTimeZone.forID("Asia/Yekaterinburg")),
                timeEnd = DateTime(p.timeEnd).withZone(DateTimeZone.forID("Asia/Yekaterinburg")),
                statusBooking = p.statusBooking
            )
        }
    }
}