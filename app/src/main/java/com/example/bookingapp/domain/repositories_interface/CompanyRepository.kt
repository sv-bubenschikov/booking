package com.example.bookingapp.domain.repositories_interface

import com.example.bookingapp.domain.entities.Company
import kotlinx.coroutines.flow.Flow

interface CompanyRepository {

    fun getCompanies(): Flow<List<Company>>

    fun getCompanyInfoById(id: Int): Company
}