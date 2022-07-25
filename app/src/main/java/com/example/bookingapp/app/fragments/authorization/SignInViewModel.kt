package com.example.bookingapp.app.fragments.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.R
import com.example.bookingapp.domain.entities.Period
import com.example.bookingapp.domain.entities.Place
import com.example.bookingapp.domain.usecases.user.SignInAsGuestUseCase
import com.example.bookingapp.domain.usecases.user.SignInUseCase
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signInAsGuestUseCase: SignInAsGuestUseCase,
) : ViewModel() {

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")

    private val _editEmailHelper = MutableStateFlow<Int?>(null)
    private val _editPasswordHelper = MutableStateFlow<Int?>(null)
    private val _dialogVisible = MutableStateFlow(false)
    private val _userSignedIn = MutableStateFlow(false)

    val editEmailHelper: StateFlow<Int?> = _editEmailHelper
    val editPasswordHelper: StateFlow<Int?> = _editPasswordHelper
    val dialogVisible: StateFlow<Boolean> = _dialogVisible
    val userSignedIn: StateFlow<Boolean> = _userSignedIn

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

    fun onUserSignInClicked() {
        _dialogVisible.value = true
        viewModelScope.launch {
            validateEmail(email.value)
            validatePassword(password.value)

            if (isValidForm()) {
                try {
                    signInUseCase(email.value, password.value)
                    _userSignedIn.value = true
                } catch (ex: FirebaseAuthInvalidUserException) {
                    _editEmailHelper.value = R.string.email_does_not_exist
                }
                catch (ex: FirebaseAuthInvalidCredentialsException) {
                    _editPasswordHelper.value = R.string.incorrect_credentials
                }
            }
            _dialogVisible.value = false
        }
    }

    fun onUserSignInAsGuestClicked() {
        _dialogVisible.value = true
        viewModelScope.launch {
            signInAsGuestUseCase()
            _userSignedIn.value = true
            _dialogVisible.value = false
        }
    }

    private fun isValidForm(): Boolean {
        return editEmailHelper.value == null && editPasswordHelper.value == null
    }

    private fun validateEmail(email: String) {
        if(email.trim().isEmpty()) {
            _editEmailHelper.value = R.string.empty_input_text
        }
        else
            _editEmailHelper.value = null
    }

    private fun validatePassword(password: String) {
        if(password.isEmpty()) {
            _editPasswordHelper.value = R.string.empty_input_text
        }
        else
            _editPasswordHelper.value = null
    }
}