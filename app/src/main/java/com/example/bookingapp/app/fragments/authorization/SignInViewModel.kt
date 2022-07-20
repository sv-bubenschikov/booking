package com.example.bookingapp.app.fragments.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.R
import com.example.bookingapp.domain.usecases.booking.CreateBookingUseCase
import com.example.bookingapp.domain.usecases.booking.DeleteBookingByIdUseCase
import com.example.bookingapp.domain.usecases.company.CreateCompanyUseCase
import com.example.bookingapp.domain.usecases.place.CreatePlaceUseCase
import com.example.bookingapp.domain.usecases.place.DeletePlaceByIdUseCase
import com.example.bookingapp.domain.usecases.user.SignInAsGuestUseCase
import com.example.bookingapp.domain.usecases.user.SignInUseCase
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signInAsGuestUseCase: SignInAsGuestUseCase,
) : ViewModel() {
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val editEmailHelper = MutableStateFlow<Int?>(null)
    val editPasswordHelper = MutableStateFlow<Int?>(null)

    init {
        viewModelScope.launch {
            email.collect {
                validateEmail(it)
            }
        }

        viewModelScope.launch {
            password.collect {
                validatePassword(it)
            }
        }
    }

    fun onUserSignInClicked(): Job {
        return viewModelScope.launch {
            validateEmail(email.value)
            validatePassword(password.value)

            if (isValidForm()) {
                try {
                    signInUseCase(email.value, password.value)
                } catch (ex: FirebaseAuthInvalidUserException) {
                    editEmailHelper.value = R.string.email_does_not_exist
                }
                catch (ex: FirebaseAuthInvalidCredentialsException) {
                    editPasswordHelper.value = R.string.incorrect_credentials
                }
            }
        }
    }

    fun onUserSignInAsGuestClicked(): Job {
        return viewModelScope.launch {
            signInAsGuestUseCase()
        }
    }

    fun isValidForm(): Boolean {
        return editEmailHelper.value == null && editPasswordHelper.value == null
    }

    private fun validateEmail(email: String) {
        if(email.trim().isEmpty()) {
            editEmailHelper.value = R.string.empty_input_text
        }
        else
            editEmailHelper.value = null
    }

    private fun validatePassword(password: String) {
        if(password.isEmpty()) {
            editPasswordHelper.value = R.string.empty_input_text
        }
        else
            editPasswordHelper.value = null
    }
}