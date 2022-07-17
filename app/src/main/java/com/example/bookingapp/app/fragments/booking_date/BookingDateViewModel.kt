package com.example.bookingapp.app.fragments.booking_date

import androidx.lifecycle.ViewModel
import com.example.bookingapp.domain.entities.Day
import com.example.bookingapp.domain.entities.Period
import com.example.bookingapp.domain.usecases.date.GetDaysInfoByPlaceIdUseCase
import com.example.bookingapp.domain.usecases.date.GetPeriodsByDayIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookingDateViewModel @Inject constructor(
    private val getDaysInfoByPlaceId: GetDaysInfoByPlaceIdUseCase,
    private val getPeriodsByDayId: GetPeriodsByDayIdUseCase,
) : ViewModel() {

    fun getDays(placeId: Int): List<Day> {
        return getDaysInfoByPlaceId(placeId)
    }

    fun getPeriods(dayId: Int): List<Period> {
        return getPeriodsByDayId(dayId)
    }

}