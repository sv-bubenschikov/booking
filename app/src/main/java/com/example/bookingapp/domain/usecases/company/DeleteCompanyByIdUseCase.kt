package com.example.bookingapp.domain.usecases.company

import com.example.bookingapp.domain.entities.Company
import com.example.bookingapp.domain.repositories_interface.CompanyRepository
import javax.inject.Inject

class DeleteCompanyByIdUseCase @Inject constructor (private val companyRepository: CompanyRepository) {
    suspend operator fun invoke(id: String) =
        companyRepository.deleteCompanyById(id)
}