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

        binding.signInBtn.setOnClickListener {
            val user = User(email = editEmail.text.toString(), password = editPassword.text.toString())
            if(viewModel.signInUser(user)) {
                findNavController().navigate(R.id.action_navigation_sign_in_to_navigation_home)
            }
            else {
                Toast.makeText(context, "Error: registering failed ", Toast.LENGTH_LONG).show() // как сделать без boolean?
            }
        }

        binding.registerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_sign_in_to_navigation_register)
        }

        binding.signInAsGuest.setOnClickListener {
            viewModel.signInAsGuest()
            findNavController().navigate(R.id.action_navigation_sign_in_to_navigation_home)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}