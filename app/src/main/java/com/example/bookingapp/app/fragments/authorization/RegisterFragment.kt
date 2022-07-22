package com.example.bookingapp.app.fragments.authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.app.HostViewModel
import com.example.bookingapp.app.ui_elements.LoadingDialog
import com.example.bookingapp.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private val viewModel: RegisterViewModel by viewModels()
    private val hostViewModel: HostViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loadingDialog = LoadingDialog(inflater, requireContext())
        val binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.editEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.email.value = text.toString()
        }

        binding.editPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.password.value = text.toString()
        }

        binding.editUserName.doOnTextChanged { text, _, _, _ ->
            viewModel.username.value = text.toString()
        }

        binding.editConfirmPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.confirmedPassword.value = text.toString()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.editEmailHelper.flowWithLifecycle(lifecycle).collect { messageIdRes ->
                binding.editEmailLayout.helperText = messageIdRes?.let { it -> getText(it) }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.editPasswordHelper.flowWithLifecycle(lifecycle).collect { messageIdRes ->
                binding.editPasswordLayout.helperText = messageIdRes?.let { it -> getText(it) }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.editUserNameHelper.flowWithLifecycle(lifecycle).collect { messageIdRes ->
                binding.editUserNameLayout.helperText = messageIdRes?.let { it -> getText(it) }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.editConfirmedPasswordHelper.flowWithLifecycle(lifecycle).collect { messageIdRes ->
                binding.editConfirmPasswordLayout.helperText = messageIdRes?.let { it -> getText(it) }
            }
        }

        binding.registerBtn.setOnClickListener {
            loadingDialog.startLoading()
            viewModel.onUserRegisterClicked().invokeOnCompletion {
                if (viewModel.isValidForm()) {
                    findNavController().navigate(R.id.action_navigation_register_to_navigation_home)
                }
                loadingDialog.dismiss()
            }
        }

        binding.signInBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }
}