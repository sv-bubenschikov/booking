package com.example.bookingapp.app.fragments.companies

import androidx.lifecycle.ViewModel
import com.example.bookingapp.domain.entities.Company
import com.example.bookingapp.domain.usecases.GetCompaniesInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CompaniesViewModel @Inject constructor(
    getCompaniesInfoUseCase: GetCompaniesInfoUseCase
) : ViewModel() {
    // TODO: Implement the ViewModel

    val companyList: StateFlow<List<Company>> = getCompaniesInfoUseCase()
}