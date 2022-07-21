package com.example.bookingapp.app.fragments.booking

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.app.HostViewModel
import com.example.bookingapp.app.fragments.deialts.BookingDetailsFragment.Companion.BOOKING_ID
import com.example.bookingapp.databinding.FragmentBookingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookingFragment : Fragment(R.layout.fragment_booking) {

    private val hostViewModel: HostViewModel by activityViewModels()
    private val viewModel: BookingViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context as AppCompatActivity

        lifecycleScope.launch {
            hostViewModel.actionButtonClicked.flowWithLifecycle(lifecycle).collect {
                findNavController().navigate(R.id.action_bookingFragment_to_companiesFragment)
            }
        }

        lifecycleScope.launch {
            hostViewModel.updateCurrentUserRef().join()
            hostViewModel.currentUserRef.flowWithLifecycle(lifecycle).collect { user ->
                if (user == null)
                    findNavController().navigate(R.id.action_navigation_home_to_navigation_sign_in)
            }
        }

        context.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_profile -> {
                        findNavController().navigate(R.id.action_bookingFragment_to_profileFragment)
                        true
                    }
                    else -> false
                }
            }
        }, this, Lifecycle.State.RESUMED)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBookingBinding.bind(view)
        val adapter = BookingListAdapter { booking ->
            val arg = Bundle().apply {
                putString(BOOKING_ID, booking.id)
            }
            findNavController().navigate(R.id.actionBookingFragment_to_bookingDetailsFragment, arg)
        }
        binding.bookingList.adapter = adapter

        hostViewModel.setActionButtonVisible(true)

        lifecycleScope.launch {
            viewModel.bookingList.flowWithLifecycle(lifecycle).collect { bookingList ->
                adapter.submitList(bookingList)
            }
        }
    }
}