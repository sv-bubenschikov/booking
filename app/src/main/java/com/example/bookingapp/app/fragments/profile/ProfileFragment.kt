package com.example.bookingapp.app.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.app.HostViewModel
import com.example.bookingapp.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private val hostViewModel: HostViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        hostViewModel.setActionButtonVisible(false)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.user?.let {
                it.flowWithLifecycle(lifecycle).collect { user ->
                    binding.editEmail.setText(user.email)
                    binding.editUserName.setText(user.name)
                }
            }
        }

        binding.logoutBtn.setOnClickListener {
            viewModel.logout()
            findNavController().navigate(R.id.profile_fragment_to_sign_in)
        }

        return binding.root
    }
}