package com.example.bookingapp.app.fragments.authorization

import androidx.lifecycle.ViewModel
import com.example.bookingapp.domain.entities.User
import com.example.bookingapp.domain.usecases.user.RegisterUserUseCase
import com.example.bookingapp.domain.usecases.user.SignInAsGuestUseCase
import com.example.bookingapp.domain.usecases.user.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val signInUseCase: SignInUseCase,
    private val signInAsGuestUseCase: SignInAsGuestUseCase
) : ViewModel() {
    fun registerUser(user: User): Boolean {
        return registerUserUseCase(user)
    }

    fun signInUser(user: User): Boolean {
        return signInUseCase(user)
    }

    fun signInAsGuest() {
        signInAsGuestUseCase()
    }
}