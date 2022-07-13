package com.example.bookingapp.app.fragments.authorization

import androidx.lifecycle.ViewModel
import com.example.bookingapp.domain.entities.User
import com.example.bookingapp.domain.usecases.user.RegisterUserUseCase
import com.example.bookingapp.domain.usecases.user.SignInAsGuestUseCase
import com.example.bookingapp.domain.usecases.user.SignInUseCase
import com.example.bookingapp.domain.usecases.user.UpdateUserInfoUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val signInUseCase: SignInUseCase,
    private val signInAsGuestUseCase: SignInAsGuestUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
) : ViewModel() {
    fun registerUser(email: String, password: String): Task<AuthResult> {
        return registerUserUseCase(email, password)
    }

    fun signInUser(email: String, password: String): Task<AuthResult> {
        return signInUseCase(email, password)
    }

    fun signInAsGuest():  Task<AuthResult> {
        return signInAsGuestUseCase()
    }

    fun updateUserInfo(user: User): Task<Void> {
        return updateUserInfoUseCase(user)
    }
}