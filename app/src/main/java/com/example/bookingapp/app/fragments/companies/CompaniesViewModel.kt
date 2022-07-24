package com.example.bookingapp.app.fragments.companies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.domain.entities.Company
import com.example.bookingapp.domain.usecases.GetCompaniesInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompaniesViewModel @Inject constructor(
    getCompaniesInfoUseCase: GetCompaniesInfoUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    private val _companyList = getCompaniesInfoUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val searchQuery: StateFlow<String> = _searchQuery

    val companyList: StateFlow<List<Company>> = _companyList.combine(_searchQuery) { list, query ->
        if (query.isEmpty())
            list
        else
            list.filter { company -> company.name.contains(query, true) }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun onNewSearchQuery(query: String) {
        _searchQuery.value = query.trim()
    }
}