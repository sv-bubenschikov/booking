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
import com.example.bookingapp.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val loadingDialog = LoadingDialog(inflater, requireContext())
        val binding = FragmentSignInBinding.inflate(inflater, container, false)


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

        binding.signInBtn.setOnClickListener {
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
}