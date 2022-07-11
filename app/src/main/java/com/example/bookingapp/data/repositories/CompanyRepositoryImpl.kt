package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.Company
import com.example.bookingapp.domain.repositories_interface.CompanyRepository
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor() : CompanyRepository {
    override fun getCompaniesInfo(): List<Company> {
        TODO("Not yet implemented")
    }

    override fun getCompanyInfoById(id: Int): Company {
        TODO("Not yet implemented")
    }
}