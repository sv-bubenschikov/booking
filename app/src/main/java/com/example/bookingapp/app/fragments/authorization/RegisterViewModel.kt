package com.example.bookingapp.app.fragments.authorization

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.R
import com.example.bookingapp.domain.entities.User
import com.example.bookingapp.domain.usecases.user.GetCurrentUserUseCase
import com.example.bookingapp.domain.usecases.user.RegisterUserUseCase
import com.example.bookingapp.domain.usecases.user.UpdateUserInfoUseCase
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    private val _editEmailHelper = MutableStateFlow<Int?>(R.string.empty_input_text)
    private val _username = MutableStateFlow("")
    private val _editUserNameHelper = MutableStateFlow<Int?>(R.string.empty_input_text)
    private val _password = MutableStateFlow("")
    private val _confirmedPassword = MutableStateFlow("")
    private val _editConfirmedPasswordHelper = MutableStateFlow<Int?>(R.string.empty_input_text)
    private val _editPasswordHelper = MutableStateFlow<Int?>(R.string.empty_input_text)
    private val _showDialog = MutableStateFlow(false)
    private val _isUserRegistered = MutableStateFlow(false)

    val showDialog: StateFlow<Boolean> = _showDialog
    val isUserRegistered: StateFlow<Boolean> = _isUserRegistered

    val editEmailHelper: StateFlow<Int?> = _editEmailHelper
    val editUserNameHelper: StateFlow<Int?> = _editUserNameHelper
    val editConfirmedPasswordHelper: StateFlow<Int?> = _editConfirmedPasswordHelper
    val editPasswordHelper: StateFlow<Int?> = _editPasswordHelper

    fun onEmailChanged(email: String) {
        if (email == _email.value) return
        viewModelScope.launch {
            validateEmail(email)
            _email.value = email
        }
    }

    fun onUserNameChanged(userName: String) {
        if (userName == _username.value) return
        viewModelScope.launch {
            validateUserName(userName)
            _username.value = userName
        }
    }

    fun onPasswordChanged(password: String) {
        if (password == _password.value) return
        viewModelScope.launch {
            validatePassword(password)
            _password.value = password
        }
    }

    fun onConfirmPasswordChanged(password: String) {
        if (password == _confirmedPassword.value) return
        viewModelScope.launch {
            validateConfirmedPassword(password)
            _confirmedPassword.value = password
        }
    }

    fun onUserRegisterClicked() {
        _showDialog.value = true
        viewModelScope.launch {
            finalValidateConfirmedPassword(_confirmedPassword.value)

            if (isValidForm()) {
                try {
                    registerUserUseCase(_email.value, _password.value)
                    updateUserInfo()
                    _isUserRegistered.value = true
                } catch (ex: FirebaseAuthWeakPasswordException) {
                    _editPasswordHelper.value = R.string.weak_password
                } catch (ex: FirebaseAuthInvalidCredentialsException) {
                    _editEmailHelper.value = R.string.invalid_email
                } catch (ex: FirebaseAuthUserCollisionException) {
                    _editEmailHelper.value = R.string.email_already_exists
                }
            }
            _showDialog.value = false
        }
    }

    private fun isValidForm(): Boolean {
        return editEmailHelper.value == null &&
                editPasswordHelper.value == null &&
                editConfirmedPasswordHelper.value == null &&
                editUserNameHelper.value == null
    }

    private fun updateUserInfo() {
        viewModelScope.launch {
            getCurrentUserUseCase()?.let {
                updateUserInfoUseCase(User(it.uid, _email.value, _username.value))
            }
        }
    }

    private fun validateEmail(email: String) {
        if (email.trim().isEmpty()) {
            _editEmailHelper.value = R.string.empty_input_text
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            _editEmailHelper.value = R.string.invalid_email
        } else
            _editEmailHelper.value = null
    }

    private fun validatePassword(password: String) {
        if (password.isEmpty()) {
            _editPasswordHelper.value = R.string.empty_input_text
        } else if (password.length < 8) {
            _editPasswordHelper.value = R.string.password_invalid_len
        } else if (!password.matches(".*[A-ZА-Я].*".toRegex())) {
            _editPasswordHelper.value = R.string.password_invalid_upper_letters
        } else if (!password.matches(".*[a-zа-я].*".toRegex())) {
            _editPasswordHelper.value = R.string.password_invalid_lower_letters
        } else if (!password.matches(".*[1-9].*".toRegex())) {
            _editPasswordHelper.value = R.string.password_invalid_numbers
        } else
            _editPasswordHelper.value = null
    }

    private fun validateUserName(userName: String) {
        _editUserNameHelper.value = if (userName.isNotEmpty()) {
            null
        } else {
            R.string.empty_input_text
        }
    }

    private fun finalValidateConfirmedPassword(confirmedPassword: String) {
        _editConfirmedPasswordHelper.value =
            if (_password.value != confirmedPassword)
                R.string.confirmed_password_does_not_match
            else
                null
    }

    private fun validateConfirmedPassword(confirmedPassword: String) {
        _editConfirmedPasswordHelper.value =
            if (confirmedPassword.isEmpty())
                R.string.empty_input_text
            else {
                null
            }
    }
}