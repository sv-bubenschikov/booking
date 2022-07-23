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
import com.example.bookingapp.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private val viewModel: SignInViewModel by viewModels()
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
        val binding = FragmentSignInBinding.inflate(inflater, container, false)

        binding.editEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.email.value = text.toString()
        }

        binding.editPassword.doOnTextChanged { text, _, _, _ ->
            viewModel.password.value = text.toString()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.editEmailHelper
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { messageIdRes ->
                    binding.editEmailLayout.helperText = messageIdRes?.let { it -> getText(it) }
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.editPasswordHelper
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { messageIdRes ->
                    binding.editPasswordLayout.helperText = messageIdRes?.let { it -> getText(it) }
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.dialogVisible
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { isVisible ->
                    if (isVisible)
                        loadingDialog.startLoading()
                    else
                        loadingDialog.dismiss()
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.userSignedIn
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { isSignedIn ->
                    if (isSignedIn)
                        findNavController().navigate(R.id.action_navigation_sign_in_to_navigation_home)
                }
        }

        binding.signInBtn.setOnClickListener {
            viewModel.onUserSignInClicked()
        }

        binding.registerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_sign_in_to_navigation_register)
        }

        binding.signInAsGuest.setOnClickListener {
            viewModel.onUserSignInAsGuestClicked()
        }

        return binding.root
    }
}