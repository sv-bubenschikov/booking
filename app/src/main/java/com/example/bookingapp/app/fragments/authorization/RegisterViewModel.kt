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

    val username = MutableStateFlow("")
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val confirmedPassword = MutableStateFlow("")
    val editEmailHelper = MutableStateFlow<Int?>(R.string.empty_input_text)
    val editUserNameHelper = MutableStateFlow<Int?>(R.string.empty_input_text)
    val editConfirmedPasswordHelper = MutableStateFlow<Int?>(R.string.empty_input_text)
    val editPasswordHelper = MutableStateFlow<Int?>(R.string.empty_input_text)

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
                    editPasswordHelper.value = R.string.weak_password
                }
                catch (ex: FirebaseAuthInvalidCredentialsException) {
                    editEmailHelper.value = R.string.invalid_email
                }
                catch (ex: FirebaseAuthUserCollisionException) {
                    editEmailHelper.value = R.string.email_already_exists
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
            val user = User(username.value, email.value)
            updateUserInfoUseCase(user)
        }
    }

    private fun validateEmail(email: String) {
        if(email.trim().isEmpty()) {
            editEmailHelper.value = R.string.empty_input_text
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            editEmailHelper.value = R.string.invalid_email
        } else
            editEmailHelper.value = null
    }

    private fun validatePassword(password: String) {
        if(password.isEmpty()) {
            editPasswordHelper.value = R.string.empty_input_text
        }
        else if(password.length < 8) {
            editPasswordHelper.value = R.string.password_invalid_len
        }

        else if(!password.matches(".*[A-ZА-Я].*".toRegex())) {
            editPasswordHelper.value = R.string.password_invalid_upper_letters
        }

        else if(!password.matches(".*[a-zа-я].*".toRegex())) {
            editPasswordHelper.value = R.string.password_invalid_lower_letters
        }

        else if(!password.matches(".*[1-9].*".toRegex())) {
            editPasswordHelper.value = R.string.password_invalid_numbers
        }
        else
            editPasswordHelper.value = null
    }

    private fun validateUserName(userName: String) {
        editUserNameHelper.value = if(userName.isNotEmpty()) {
            null
        } else {
            R.string.empty_input_text
        }
    }

    private fun finalValidateConfirmedPassword(confirmedPassword: String) {
        editConfirmedPasswordHelper.value =
        if(password.value != confirmedPassword)
            R.string.confirmed_password_does_not_match
        else
            null
    }

    private fun validateConfirmedPassword(confirmedPassword: String) {
        editConfirmedPasswordHelper.value =
            if(confirmedPassword.isEmpty())
                R.string.empty_input_text
            else {
                null
            }
    }
}