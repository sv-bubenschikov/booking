package com.example.bookingapp.app.fragments.authorization

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.bookingapp.domain.entities.User
import com.example.bookingapp.domain.usecases.user.RegisterUserUseCase
import com.example.bookingapp.domain.usecases.user.UpdateUserInfoUseCase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
) : ViewModel() {

    val username = MutableStateFlow("")
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val confirmedPassword = MutableStateFlow("")

    suspend fun registerUser(): AuthResult? {
        val email = email.value.apply { if(trim().isEmpty()) return null }
        val password = password.value.apply { if(trim().isEmpty()) return null }
        val confirmedPassword = confirmedPassword.value.apply { if(trim().isEmpty()) return null }
        val name = username.value.apply { if(trim().isEmpty()) return null }
        if(password != confirmedPassword)
            return null
        return registerUserUseCase(email, password)

    }

    suspend fun updateUserInfo(): Boolean {
        val userRef = FirebaseAuth.getInstance().currentUser.apply { if(this == null) return false}
        val email = email.value.apply { if(trim().isEmpty()) return false }
        val name = username.value.apply { if(trim().isEmpty()) return false }
        val user = User(userRef!!.uid, name, email)
        return updateUserInfoUseCase(user)
    }
}