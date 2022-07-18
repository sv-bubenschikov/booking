package com.example.bookingapp.app.fragments.authorization

import android.annotation.SuppressLint
import android.content.Context
import android.util.Patterns
import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import com.example.bookingapp.R
import com.example.bookingapp.app.ui_elements.LoadingDialog
import com.example.bookingapp.domain.entities.User
import com.example.bookingapp.domain.usecases.user.RegisterUserUseCase
import com.example.bookingapp.domain.usecases.user.UpdateUserInfoUseCase
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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
    val editEmailHelper = MutableStateFlow<String?>(null)
    val editUserNameHelper = MutableStateFlow<String?>(null)
    val editConfirmedPasswordHelper = MutableStateFlow<String?>(null)
    val editPasswordHelper = MutableStateFlow<String?>(null)
    private lateinit var loadingDialog: LoadingDialog
    @SuppressLint("StaticFieldLeak")
    private lateinit var context: Context

    fun initContext(inflater: LayoutInflater, context: Context) {
        loadingDialog = LoadingDialog(inflater, context)
        this.context = context
    }

    suspend fun registerUser(): AuthResult? {
        return if(submitForm()) {
            registerUserUseCase(email.value, password.value)
        } else
            null
    }

    suspend fun updateUserInfo(): Boolean {
        val userRef = FirebaseAuth.getInstance().currentUser.apply { if(this == null) return false}
        return if(submitForm()) {
            val user = User(userRef!!.uid, username.value, email.value)
            updateUserInfoUseCase(user)
        } else false
    }

    fun emailChangeListener() {
        editEmailHelper.value = validEmail(email.value)
    }

    private fun validEmail(str: String): String? {
        if(!Patterns.EMAIL_ADDRESS.matcher(str).matches()) {
            return context.getString(R.string.invalid_email)
        }
        return null
    }

    fun userNameChangeListener() {
        editUserNameHelper.value = validUserName(username.value)
    }

    private fun validUserName(str: String): String? {
        return if(str.isNotEmpty()) {
            null
        } else {
            context.getString(R.string.empty_input_text)
        }
    }

    fun passwordChangeListener() {
        editPasswordHelper.value = validPassword(password.value)
    }

    fun confirmedPasswordChangeListener() {
        val valid = validPassword(confirmedPassword.value)
        if(valid == null) {
            if(confirmedPassword.value == password.value) {
                editConfirmedPasswordHelper.value = valid
            }
            else {
                editConfirmedPasswordHelper.value = context.getString(R.string.confirmed_password_is_not_matching)
            }
        }
        else
            editConfirmedPasswordHelper.value = valid
    }

    private fun validPassword(password: String): String? {
        if(password.length < 8) {
            return context.getString(R.string.password_invalid_len)
        }

        else if(!password.matches(".*[A-ZА-Я].*".toRegex())) {
            return context.getString(R.string.password_invalid_upper_letters)
        }

        else if(!password.matches(".*[a-zа-я].*".toRegex())) {
            return context.getString(R.string.password_invalid_lower_letters)
        }

        else if(!password.matches(".*[1-9].*".toRegex())) {
            return context.getString(R.string.password_invalid_numbers)
        }
        else
            return null
    }

    private fun submitForm(): Boolean {
        val validEmail = editEmailHelper.value == null
        val validPassword = editPasswordHelper.value == null
        val validConfirmedPassword = editConfirmedPasswordHelper.value == null
        val validUsername = editUserNameHelper.value == null

        return if(validEmail && validPassword && validUsername && validConfirmedPassword) {
            true
        } else {
            invalidForm()
            false
        }
    }

    private fun invalidForm() {
        var message = ""
        if(editUserNameHelper.value != null)
            message += "\n\nФио: ${editUserNameHelper.value}"
        if(editEmailHelper.value != null)
            message += "\n\nЛогин: ${editEmailHelper.value}"
        if(editPasswordHelper.value != null)
            message += "\n\nПароль: ${editPasswordHelper.value}"
        if(editConfirmedPasswordHelper.value != null)
            message += "\n\nПодтвержденный пароль: ${editConfirmedPasswordHelper.value}"

        MaterialAlertDialogBuilder(context)
            .setTitle(context.getString(R.string.invalid_input_form_title))
            .setMessage(message)
            .setPositiveButton(context.getString(R.string.accept_invalid_form_btn)) { _, _ ->
                //do nothing
            }.show()
    }
}