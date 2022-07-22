package com.example.bookingapp.app.fragments.authorization

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.R
import com.example.bookingapp.domain.entities.User
import com.example.bookingapp.domain.usecases.user.GetCurrentUserRefUseCase
import com.example.bookingapp.domain.usecases.user.RegisterUserUseCase
import com.example.bookingapp.domain.usecases.user.UpdateUserInfoUseCase
import com.google.firebase.auth.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase,
    private val getCurrentUserRefUseCase: GetCurrentUserRefUseCase
) : ViewModel() {

    private val _editEmailHelper = MutableStateFlow<Int?>(R.string.empty_input_text)
    private val _editUserNameHelper = MutableStateFlow<Int?>(R.string.empty_input_text)
    private val _editConfirmedPasswordHelper = MutableStateFlow<Int?>(R.string.empty_input_text)
    private val _editPasswordHelper = MutableStateFlow<Int?>(R.string.empty_input_text)

    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val username = MutableStateFlow("")
    val confirmedPassword = MutableStateFlow("")

    val editEmailHelper: StateFlow<Int?> = _editEmailHelper
    val editUserNameHelper: StateFlow<Int?> = _editUserNameHelper
    val editConfirmedPasswordHelper: StateFlow<Int?> = _editConfirmedPasswordHelper
    val editPasswordHelper: StateFlow<Int?> = _editPasswordHelper

    init {
        viewModelScope.launch {
            username.collect {
                validateUserName(it)
            }
        }

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

        viewModelScope.launch {
            confirmedPassword.collect {
                validateConfirmedPassword(it)
            }
        }
    }

    fun onUserRegisterClicked(): Job {
        return viewModelScope.launch {
            finalValidateConfirmedPassword(confirmedPassword.value)

            if(isValidForm()) {
                try {
                    registerUserUseCase(email.value, password.value)
                    updateUserInfo()
                }
                catch (ex: FirebaseAuthWeakPasswordException) {
                    _editPasswordHelper.value = R.string.weak_password
                }
                catch (ex: FirebaseAuthInvalidCredentialsException) {
                    _editEmailHelper.value = R.string.invalid_email
                }
                catch (ex: FirebaseAuthUserCollisionException) {
                    _editEmailHelper.value = R.string.email_already_exists
                }
            }
        }
    }

    fun isValidForm(): Boolean {
        return editEmailHelper.value == null &&
                editPasswordHelper.value == null &&
                editConfirmedPasswordHelper.value == null &&
                editUserNameHelper.value == null
    }

    private fun updateUserInfo() {
        viewModelScope.launch {
            getCurrentUserRefUseCase()?.let {
                updateUserInfoUseCase(User(it.uid, email.value, username.value))
            }
        }
    }

    private fun validateEmail(email: String) {
        if(email.trim().isEmpty()) {
            _editEmailHelper.value = R.string.empty_input_text
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            _editEmailHelper.value = R.string.invalid_email
        } else
            _editEmailHelper.value = null
    }

    private fun validatePassword(password: String) {
        if(password.isEmpty()) {
            _editPasswordHelper.value = R.string.empty_input_text
        }
        else if(password.length < 8) {
            _editPasswordHelper.value = R.string.password_invalid_len
        }

        else if(!password.matches(".*[A-ZА-Я].*".toRegex())) {
            _editPasswordHelper.value = R.string.password_invalid_upper_letters
        }

        else if(!password.matches(".*[a-zа-я].*".toRegex())) {
            _editPasswordHelper.value = R.string.password_invalid_lower_letters
        }

        else if(!password.matches(".*[1-9].*".toRegex())) {
            _editPasswordHelper.value = R.string.password_invalid_numbers
        }
        else
            _editPasswordHelper.value = null
    }

    private fun validateUserName(userName: String) {
        _editUserNameHelper.value = if(userName.isNotEmpty()) {
            null
        } else {
            R.string.empty_input_text
        }
    }

    private fun finalValidateConfirmedPassword(confirmedPassword: String) {
        _editConfirmedPasswordHelper.value =
        if(password.value != confirmedPassword)
            R.string.confirmed_password_does_not_match
        else
            null
    }

    private fun validateConfirmedPassword(confirmedPassword: String) {
        _editConfirmedPasswordHelper.value =
            if(confirmedPassword.isEmpty())
                R.string.empty_input_text
            else {
                null
            }
    }
}