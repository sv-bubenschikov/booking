package com.example.bookingapp.domain.repositories_interface

import com.example.bookingapp.domain.entities.Company

interface CompanyRepository {

    fun getCompaniesInfo(): List<Company>

    fun getCompanyInfoById(id: Int): Company

    suspend fun createCompany(company: Company): Void

    suspend fun deleteCompanyById(id: String): Void

}