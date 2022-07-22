package com.example.bookingapp.app.fragments.companies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.domain.usecases.GetCompaniesInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CompaniesViewModel @Inject constructor(
    getCompaniesInfoUseCase: GetCompaniesInfoUseCase
) : ViewModel() {

    val companies = getCompaniesInfoUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
}