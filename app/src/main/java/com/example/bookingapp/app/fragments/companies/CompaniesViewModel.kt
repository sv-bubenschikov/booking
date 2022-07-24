package com.example.bookingapp.app.fragments.companies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
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

    init {
        viewModelScope.launch {
            _companyListFromFB.collect {
                val filterText = searchText.value
                if(filterText.isEmpty()) {
                    _companyList.emit(it)
                }
                else {
                    val filteredList = it.filter { company ->
                        company.name.lowercase().contains(filterText.lowercase())
                    }

                    _companyList.emit(filteredList)
                }
            }
        }

        viewModelScope.launch {
            searchText.collect {
                val filteredList = _companyListFromFB.value.filter { company ->
                    company.name.lowercase().contains(searchText.value.lowercase())
                }

                _companyList.emit(filteredList)
            }
        }
    }

    private val _companyListFromFB = getCompaniesInfoUseCase()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _companyList = MutableStateFlow(_companyListFromFB.value)

    val companyList: StateFlow<List<Company>> = _companyList
    val searchText = MutableStateFlow("")
}