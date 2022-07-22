package com.example.bookingapp.app.fragments.deialts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.bookingapp.domain.entities.Booking
import com.example.bookingapp.domain.entities.Company
import com.example.bookingapp.domain.usecases.GetCompanyInfoById
import com.example.bookingapp.domain.usecases.booking.GetBookingInfoByIdUseCase
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CompaniesDetailsViewModel(
getCompanyInfoById: GetCompanyInfoById,
stateHandle: SavedStateHandle
) : ViewModel() {

    val company: StateFlow<Company> = getCompanyInfoById(stateHandle[CompaniesDetailsFragment.Company_Id]!!)
}