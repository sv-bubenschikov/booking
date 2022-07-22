package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.Booking
import com.example.bookingapp.domain.entities.BookingDate
import com.example.bookingapp.domain.entities.Company
import com.example.bookingapp.domain.entities.Place
import com.example.bookingapp.domain.repositories_interface.CompanyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor() : CompanyRepository {
    override fun getCompaniesInfo() = MutableStateFlow(
        listOf(
            Company(0,"Тензор",""),
            Company(1,"Газпром",""),
        )
    )

    override fun getCompanyInfoById(id: Int) = MutableStateFlow(
        Company(
            id,
            "Тензор",
            ""
        )
    )
}