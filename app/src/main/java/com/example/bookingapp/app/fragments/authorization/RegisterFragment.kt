package com.example.bookingapp.app.fragments.authorization

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.app.ui_elements.LoadingDialog
import com.example.bookingapp.databinding.FragmentRegisterBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private val viewModel: RegisterViewModel by viewModels()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loadingDialog = LoadingDialog(inflater, requireContext())
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        emailFocusListener()
        passwordFocusListener()
        userNameFocusListener()
        confirmedPasswordFocusListener()



        binding.registerBtn.setOnClickListener {
            if(submitForm()) {
                lifecycleScope.launch {
                    loadingDialog.startLoading()
                    if(viewModel.registerUser() != null) {
                        viewModel.updateUserInfo()
                        findNavController().navigate(R.id.action_navigation_register_to_navigation_home)
                    }
                    else {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
                    }
                    loadingDialog.dismiss()
                }
            }
        }

        binding.signInBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }

    private fun emailFocusListener() {
        binding.editEmail.doOnTextChanged { text, _, _, _ ->
            val strText = text.toString()
            binding.editEmailLayout.helperText = validEmail(strText)
        }
    }

    private fun validEmail(str: String): String? {
        if(!Patterns.EMAIL_ADDRESS.matcher(str).matches()) {
            return "Неверный формат почты"
        }
        return null
    }

    private fun userNameFocusListener() {
        binding.editUserName.doOnTextChanged { text, _, _, _ ->
            val strText = text.toString()
            binding.editUserNameLayout.helperText = validUserName(strText)
        }
    }

    private fun validUserName(str: String): String? {
        return if(str.isNotEmpty()) {
            null
        } else {
            "Незаполненное поле ввода"
        }
    }

    private fun passwordFocusListener() {
        binding.editPassword.doOnTextChanged { text, _, _, _ ->
            val strText = text.toString()
            binding.editPasswordLayout.helperText = validPassword(strText)
        }
    }

    private fun confirmedPasswordFocusListener() {
        binding.editConfirmPassword.doOnTextChanged { text, _, _, _ ->
            val strText = text.toString()
            val valid = validPassword(strText)
            if(valid == null) {
                if(strText == viewModel.password.value) {
                    binding.editConfirmPasswordLayout.helperText = valid
                }
                else {
                    binding.editConfirmPasswordLayout.helperText = "Подтверждённый пароль не совпадает"
                }
            }
            else
                binding.editConfirmPasswordLayout.helperText = valid
        }
    }

    private fun validPassword(password: String): String? {
        if(password.length < 8) {
            return "Пароль должен состоять минимум из 8 символов"
        }

        else if(!password.matches(".*[A-ZА-Я].*".toRegex())) {
            return "Пароль должен содержать минимум 1 заглавную букву"
        }

        else if(!password.matches(".*[a-zа-я].*".toRegex())) {
            return "Пароль должен содержать минимум 1 строчную букву"
        }

        else if(!password.matches(".*[1-9].*".toRegex())) {
            return "Пароль должен содержать минимум 1 цифру"
        }
        else
            return null
    }

    private fun submitForm(): Boolean {
        val validEmail = binding.editEmailLayout.helperText == null
        val validPassword = binding.editPasswordLayout.helperText == null
        val validConfirmedPassword = binding.editConfirmPasswordLayout.helperText == null
        val validUsername = binding.editUserNameLayout.helperText == null

        return if(validEmail && validPassword && validUsername && validConfirmedPassword) {
            true
        } else {
            invalidForm()
            false
        }
    }

    private fun invalidForm() {
        var message = ""
        if(binding.editUserNameLayout.helperText != null)
            message += "\n\nФио: ${binding.editUserNameLayout.helperText}"
        if(binding.editEmailLayout.helperText != null)
            message += "\n\nЛогин: ${binding.editEmailLayout.helperText}"
        if(binding.editPasswordLayout.helperText != null)
            message += "\n\nПароль: ${binding.editPasswordLayout.helperText}"
        if(binding.editConfirmPasswordLayout.helperText != null)
            message += "\n\nПодтвержденный пароль: ${binding.editConfirmPasswordLayout.helperText}"

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Ошибка ввода")
            .setMessage(message)
            .setPositiveButton("Ок") {_,_ ->
                //do nothing
            }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}