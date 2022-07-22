package com.example.bookingapp.domain.usecases

import com.example.bookingapp.domain.entities.Company
import com.example.bookingapp.domain.repositories_interface.CompanyRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetCompaniesInfoUseCase @Inject constructor(private val companyRepository: CompanyRepository) {
    operator fun invoke() = companyRepository.getCompaniesInfo()
}