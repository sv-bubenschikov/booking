package com.example.bookingapp.domain.usecases.company

import com.example.bookingapp.domain.entities.Booking
import com.example.bookingapp.domain.entities.Company
import com.example.bookingapp.domain.repositories_interface.BookingRepository
import com.example.bookingapp.domain.repositories_interface.CompanyRepository
import javax.inject.Inject

class CreateCompanyUseCase @Inject constructor (private val companyRepository: CompanyRepository) {
    suspend operator fun invoke(company: Company) =
        companyRepository.createCompany(company)
}