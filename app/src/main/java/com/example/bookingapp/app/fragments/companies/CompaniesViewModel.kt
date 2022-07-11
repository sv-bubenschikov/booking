package com.example.bookingapp.app.fragments.companies

import androidx.lifecycle.ViewModel
import com.example.bookingapp.domain.usecases.GetCompaniesInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CompaniesViewModel @Inject constructor(
    private val getCompaniesInfoUseCase: GetCompaniesInfoUseCase
) : ViewModel() {
    // TODO: Implement the ViewModel
}