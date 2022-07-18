package com.example.bookingapp.app.fragments.authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.app.ui_elements.LoadingDialog
import com.example.bookingapp.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loadingDialog = LoadingDialog(inflater, requireContext())
        val binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.initContext(inflater, requireContext())
        lifecycleScope.launch {
            viewModel.email.flowWithLifecycle(lifecycle).collect {
                viewModel.emailChangeListener()
                binding.editEmailLayout.helperText = viewModel.editEmailHelper.value
            }
        }

        lifecycleScope.launch {
            viewModel.password.flowWithLifecycle(lifecycle).collect {
                viewModel.passwordChangeListener()
                binding.editPasswordLayout.helperText = viewModel.editPasswordHelper.value
            }
        }

        lifecycleScope.launch {
            viewModel.username.flowWithLifecycle(lifecycle).collect {
                viewModel.userNameChangeListener()
                binding.editUserNameLayout.helperText = viewModel.editUserNameHelper.value
            }
        }

        lifecycleScope.launch {
            viewModel.confirmedPassword.flowWithLifecycle(lifecycle).collect {
                viewModel.confirmedPasswordChangeListener()
                binding.editConfirmPasswordLayout.helperText = viewModel.editConfirmedPasswordHelper.value
            }
        }




        binding.registerBtn.setOnClickListener {
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

        binding.signInBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }
}