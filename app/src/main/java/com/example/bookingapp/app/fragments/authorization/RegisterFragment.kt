package com.example.bookingapp.app.fragments.authorization

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.databinding.FragmentRegisterBinding
import com.example.bookingapp.domain.entities.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthorizationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val editEmail = binding.editEmail
        val editPassword = binding.editPassword
        val editConfirmPassword = binding.editConfirmPassword
        val editName = binding.editUserName

        binding.registerBtn.setOnClickListener {
            if(editPassword.text.toString() == editConfirmPassword.text.toString() ) {
                val user = User(
                    name = editName.text.toString(),
                    email = editEmail.text.toString(),
                    password = editPassword.text.toString()
                )
                val isSuccess = viewModel.registerUser(user)
                findNavController().navigate(R.id.action_navigation_register_to_navigation_home)
//                if(isSuccess) {
//                    findNavController().navigate(R.id.action_navigation_register_to_navigation_home)
//                }
//                else {
//                    Toast.makeText(context, "Error: registering failed ", Toast.LENGTH_LONG).show() // как сделать без boolean
//                }
            }
            else
                Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_LONG).show()
        }

        binding.signInBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}