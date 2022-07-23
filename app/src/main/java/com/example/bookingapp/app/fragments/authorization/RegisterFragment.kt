package com.example.bookingapp.app.fragments.authorization

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
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
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private val viewModel: RegisterViewModel by viewModels()
    private val hostViewModel: HostViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        hostViewModel.setActionButtonVisible(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loadingDialog = LoadingDialog(inflater, requireContext())
        val binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.editEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.onEmailChanged(text.toString())
        }

        binding.editPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.onPasswordChanged(text.toString())
        }

        binding.editUserName.doOnTextChanged { text, _, _, _ ->
            viewModel.onUserNameChanged(text.toString())
        }

        binding.editConfirmPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.onConfirmPasswordChanged(text.toString())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.editEmailHelper
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { messageRes ->
                    binding.editEmailLayout.helperText = messageRes?.let { it -> getText(it) }
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.editPasswordHelper
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { messageRes ->
                    binding.editPasswordLayout.helperText = messageRes?.let { it -> getText(it) }
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.editUserNameHelper
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { messageRes ->
                    binding.editUserNameLayout.helperText = messageRes?.let { it -> getText(it) }
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.editConfirmedPasswordHelper
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { messageRes ->
                    binding.editConfirmPasswordLayout.helperText =
                        messageRes?.let { it -> getText(it) }
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.showDialog
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { isVisible ->
                    if (isVisible)
                        loadingDialog.startLoading()
                    else
                        loadingDialog.dismiss()
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isUserRegistered
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { userRegistered ->
                    if (userRegistered)
                        findNavController().navigate(R.id.action_navigation_register_to_navigation_home)
                }
        }

        binding.registerBtn.setOnClickListener {
            viewModel.onUserRegisterClicked()
        }

        binding.signInBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }
}