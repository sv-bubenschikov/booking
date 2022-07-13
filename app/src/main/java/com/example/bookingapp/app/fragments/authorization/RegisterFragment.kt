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
import com.example.bookingapp.app.fragments.ui_elements.LoadingDialog
import com.example.bookingapp.databinding.FragmentRegisterBinding
import com.example.bookingapp.domain.entities.User
import com.google.firebase.auth.FirebaseAuth
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
        val mAuth = FirebaseAuth.getInstance()
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val editEmail = binding.editEmail
        val editPassword = binding.editPassword
        val editConfirmPassword = binding.editConfirmPassword
        val editName = binding.editUserName

        binding.registerBtn.setOnClickListener {
            val loadingDialog = LoadingDialog(inflater, context!!)
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            val userName = editName.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty() && userName.isNotEmpty()) {
                if(editPassword.text.toString() == editConfirmPassword.text.toString() ) {
                    loadingDialog.startLoading()
                    viewModel.registerUser(editEmail.text.toString(), editPassword.text.toString()).addOnCompleteListener {
                        if(it.isSuccessful) {
                            val user = User(
                                mAuth.currentUser!!.uid,
                                editName.text.toString(),
                                editEmail.text.toString(),
                            )
                            viewModel.updateUserInfo(user).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    findNavController().navigate(R.id.action_navigation_register_to_navigation_home)
                                }
                                else {
                                    val msgError = task.exception.toString().substringAfter(':')
                                    Toast.makeText(context, "Error: $msgError", Toast.LENGTH_LONG).show()
                                    mAuth.signOut()
                                }
                            }
                        }
                        else {
                            val msg = it.exception.toString().substringAfter(':')
                            Toast.makeText(context, "Error: $msg", Toast.LENGTH_LONG).show()
                            mAuth.signOut()
                        }
                        loadingDialog.dismiss()
                    }
                }
                else
                    Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_LONG).show()
            }
            else
                Toast.makeText(context, "Указаны не все данные", Toast.LENGTH_SHORT).show()

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