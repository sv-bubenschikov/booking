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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.app.ui_elements.LoadingDialog
import com.example.bookingapp.databinding.FragmentSignInBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private val viewModel: SignInViewModel by viewModels()
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loadingDialog = LoadingDialog(inflater, requireContext())
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        emailFocusListener()
        passwordFocusListener()


        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.signInBtn.setOnClickListener {
            if(submitForm()) {
                lifecycleScope.launch {
                    loadingDialog.startLoading()
                    if (viewModel.signInUser() != null) {
                        findNavController().navigate(R.id.action_navigation_sign_in_to_navigation_home)
                    } else {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
                    }

                    loadingDialog.dismiss()
                }
            }
        }

        binding.registerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_sign_in_to_navigation_register)
        }

        binding.signInAsGuest.setOnClickListener {
            lifecycleScope.launch {
                loadingDialog.startLoading()
                if (viewModel.signInAsGuest() != null) {
                    findNavController().navigate(R.id.action_navigation_sign_in_to_navigation_home)
                } else {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
                }

                loadingDialog.dismiss()
            }
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

    private fun passwordFocusListener() {
        binding.editPassword.doOnTextChanged { text, _, _, _ ->
            val strText = text.toString()
            binding.editPasswordLayout.helperText = validPassword(strText)
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

        return if(validEmail && validPassword) {
            true
        } else {
            invalidForm()
            false
        }
    }

    private fun invalidForm() {
        var message = ""
        if(binding.editEmailLayout.helperText != null)
            message += "\n\nЛогин: ${binding.editEmailLayout.helperText}"
        if(binding.editPasswordLayout.helperText != null)
            message += "\n\nПароль: ${binding.editPasswordLayout.helperText}"

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