package com.example.bookingapp.app.fragments.authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.app.fragments.booking.BookingViewModel
import com.example.bookingapp.app.fragments.ui_elements.LoadingDialog
import com.example.bookingapp.data.repositories.UserRepositoryImpl
import com.example.bookingapp.databinding.FragmentSignInBinding
import com.example.bookingapp.domain.entities.User
import com.example.bookingapp.domain.repositories_interface.UserRepository
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthorizationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val editEmail = binding.editEmail
        val editPassword = binding.editPassword
        val loadingDialog = LoadingDialog(inflater, context!!)

        binding.signInBtn.setOnClickListener {
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty()) {
                loadingDialog.startLoading()
                viewModel.signInUser(email, password)
                    .addOnCompleteListener {
                        if(it.isSuccessful) {
                            findNavController().navigate(R.id.action_navigation_sign_in_to_navigation_home)
                        }
                        else {
                            val msg = it.exception.toString().substringAfter(':')
                            Toast.makeText(context, "Error: $msg", Toast.LENGTH_LONG).show()
                        }
                        loadingDialog.dismiss()
                    }
            }
            else {
                Toast.makeText(context, "Указаны не все данные", Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_sign_in_to_navigation_register)
        }

        binding.signInAsGuest.setOnClickListener {
            loadingDialog.startLoading()
            viewModel.signInAsGuest().addOnCompleteListener {
                if(it.isSuccessful) {
                    findNavController().navigate(R.id.action_navigation_sign_in_to_navigation_home)
                }
                else {
                    val msg = it.exception.toString().substringAfter(':')
                    Toast.makeText(context, "Error: $msg", Toast.LENGTH_LONG).show()
                }

                loadingDialog.dismiss()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}