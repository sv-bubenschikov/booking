package com.example.bookingapp.app.fragments.authorization

import android.annotation.SuppressLint
import android.content.Context
import android.util.Patterns
import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import com.example.bookingapp.R
import com.example.bookingapp.app.ui_elements.LoadingDialog
import com.example.bookingapp.domain.usecases.user.SignInAsGuestUseCase
import com.example.bookingapp.domain.usecases.user.SignInUseCase
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signInAsGuestUseCase: SignInAsGuestUseCase,
) : ViewModel() {
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val editEmailHelper = MutableStateFlow<String?>(null)
    val editPasswordHelper = MutableStateFlow<String?>(null)
    private lateinit var loadingDialog: LoadingDialog
    @SuppressLint("StaticFieldLeak")
    private lateinit var context: Context

    fun initContext(inflater: LayoutInflater, context: Context) {
        loadingDialog = LoadingDialog(inflater, context)
        this.context = context
    }

    suspend fun signInUser(): AuthResult? {
        return if(submitForm(context)) {
            signInUseCase(email.value, password.value)
        } else null
    }

    suspend fun signInAsGuest(): AuthResult? {
        return signInAsGuestUseCase()
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

    fun passwordChangeListener() {
        editPasswordHelper.value = validPassword(password.value)
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

    private fun submitForm(context: Context): Boolean {
        val validEmail = editEmailHelper.value == null
        val validPassword = editPasswordHelper.value == null

        return if(validEmail && validPassword) {
            true
        } else {
            invalidForm(context)
            false
        }
    }

    private fun invalidForm(context: Context) {
        var message = ""
        if(editEmailHelper.value != null)
            message += "\n\nЛогин: ${editEmailHelper.value}"
        if(editPasswordHelper.value != null)
            message += "\n\nПароль: ${editPasswordHelper.value}"

        MaterialAlertDialogBuilder(context)
            .setTitle(context.getString(R.string.invalid_input_form_title))
            .setMessage(message)
            .setPositiveButton(context.getString(R.string.accept_invalid_form_btn)) { _, _ ->
                //do nothing
            }.show()
    }
}