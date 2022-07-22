package com.example.bookingapp.domain.usecases

import com.example.bookingapp.domain.repositories_interface.CompanyRepository
import javax.inject.Inject

class GetCompanyInfoById @Inject constructor(private val companyRepository: CompanyRepository) {
    operator fun invoke(id : Int) = companyRepository.getCompanyInfoById (id)
}