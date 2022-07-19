package com.example.bookingapp.app.fragments.booking_date

import androidx.lifecycle.ViewModel
import com.example.bookingapp.app.entities.PeriodForFragment
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
) : ViewModel() {

    val days: StateFlow<List<Day>> = getDaysInfoByPlaceId(1) //TODO Получение по id метса

    val periods: MutableStateFlow<List<PeriodForFragment>> = MutableStateFlow(emptyList())

    fun getPeriods(dayId: Int) {
        periods.value = getPeriodsByDayId(dayId).value.map { p ->
            PeriodForFragment(
                id = p.id,
                timeStart = DateTime(p.timeStart).withZone(DateTimeZone.forID("Asia/Yekaterinburg")),
                timeEnd = DateTime(p.timeEnd).withZone(DateTimeZone.forID("Asia/Yekaterinburg")),
                statusBooking = p.statusBooking
            )
        }
    }
}