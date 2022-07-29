package com.example.bookingapp.app.fragments.booking

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
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
        val adapter = BookingListAdapter { bookingId ->
            val arg = Bundle().apply {
                putString(BOOKING_ID, bookingId)
            }
            findNavController().navigate(R.id.actionBookingFragment_to_bookingDetailsFragment, arg)
        }
        binding.bookingList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.bookingList
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { bookingList ->
                    adapter.submitList(bookingList)
                    binding.emptyBookingsPlaceholder.isVisible = bookingList.isEmpty()
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            hostViewModel.actionButtonClicked.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect {
                    findNavController().navigate(R.id.action_bookingFragment_to_companiesFragment)
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.currentUserRef.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect {
                    if (it == null)
                        findNavController().navigate(R.id.action_navigation_home_to_navigation_sign_in)
                }
        }
    }

    override fun onStart() {
        super.onStart()

        hostViewModel.setActionButtonVisible(true)
        hostViewModel.setToolbarTitle(getString(R.string.booking_fragment_label))
    }
}