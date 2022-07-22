package com.example.bookingapp.app.fragments.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.domain.entities.User
import com.example.bookingapp.domain.usecases.user.GetUserInfoByIdUseCase
import com.example.bookingapp.domain.usecases.user.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getUserInfoByIdUseCase: GetUserInfoByIdUseCase,
) : ViewModel() {

    val user = getUserInfoByIdUseCase()
        ?.stateIn(viewModelScope, SharingStarted.Eagerly, User())

    fun logout() {
        logoutUseCase()
    }
}

