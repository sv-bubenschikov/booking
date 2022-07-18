package com.example.bookingapp.app.fragments.authorization

import androidx.lifecycle.ViewModel
import com.example.bookingapp.domain.entities.User
import com.example.bookingapp.domain.usecases.user.RegisterUserUseCase
import com.example.bookingapp.domain.usecases.user.SignInAsGuestUseCase
import com.example.bookingapp.domain.usecases.user.SignInUseCase
import com.example.bookingapp.domain.usecases.user.UpdateUserInfoUseCase
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signInAsGuestUseCase: SignInAsGuestUseCase,
) : ViewModel() {
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    suspend fun signInUser(): AuthResult? {
        val email = email.value.apply { if(trim().isEmpty()) return null }
        val password = password.value.apply { if(trim().isEmpty()) return null }

        return signInUseCase(email, password)
    }

    suspend fun signInAsGuest(): AuthResult? {
        return signInAsGuestUseCase()
    }
}