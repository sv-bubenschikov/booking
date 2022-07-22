package com.example.bookingapp.domain.repositories_interface

import com.example.bookingapp.domain.entities.Company
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface CompanyRepository {

    fun getCompaniesInfo(): StateFlow<List<Company>>

    fun getCompanyInfoById(id: Int): StateFlow<Company>
}