package com.example.bookingapp.app.fragments.booking

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.app.fragments.companies.CompaniesFragment
import com.example.bookingapp.databinding.FragmentBookingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingFragment : Fragment(R.layout.fragment_booking) {

    private val viewModel: BookingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBookingBinding.bind(view)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_profile -> {
                        findNavController(view).navigate(R.id.action_bookingFragment_to_profileFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.addBooking.setOnClickListener {
            findNavController(view).navigate(R.id.action_bookingFragment_to_companiesFragment)
        }
    }

}