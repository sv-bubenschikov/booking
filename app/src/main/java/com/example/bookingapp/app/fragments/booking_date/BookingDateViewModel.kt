package com.example.bookingapp.app.fragments.booking_date

import androidx.lifecycle.ViewModel
import com.example.bookingapp.app.entities.PeriodForFragment
import com.example.bookingapp.domain.entities.Day
import com.example.bookingapp.domain.usecases.date.GetDaysInfoByPlaceIdUseCase
import com.example.bookingapp.domain.usecases.date.GetPeriodsByDayIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import javax.inject.Inject

@HiltViewModel
class BookingDateViewModel @Inject constructor(
    private val getDaysInfoByPlaceId: GetDaysInfoByPlaceIdUseCase,
    private val getPeriodsByDayId: GetPeriodsByDayIdUseCase,
) : ViewModel() {

    fun getDays(placeId: Int): List<Day> = getDaysInfoByPlaceId(placeId)

    fun getPeriods(dayId: Int): List<PeriodForFragment> = getPeriodsByDayId(dayId).map { p ->
        PeriodForFragment(
            id = p.id,
            timeStart = DateTime(p.timeStart).withZone(DateTimeZone.forID("Asia/Yekaterinburg")),
            timeEnd = DateTime(p.timeEnd).withZone(DateTimeZone.forID("Asia/Yekaterinburg")),
            statusBooking = p.statusBooking
        )
    }
}