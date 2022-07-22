package com.example.bookingapp.app.fragments.companies

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
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
import com.example.bookingapp.app.fragments.deialts.BookingDetailsFragment
import com.example.bookingapp.app.fragments.deialts.CompaniesDetailsFragment
import com.example.bookingapp.databinding.FragmentCompaniesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompaniesFragment : Fragment(R.layout.fragment_companies) {

    private val hostViewModel: HostViewModel by activityViewModels()
    private val viewModel: CompaniesViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context as AppCompatActivity

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
                        findNavController().navigate(R.id.action_companies_fragment_to_profile_fragment)
                        true
                    }
                    else -> false
                }
            }
        }, this, Lifecycle.State.RESUMED)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCompaniesBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        val adapter = CompaniesListAdapter {
            findNavController().navigate(R.id.action_companiesFragment_to_placesFragment)
        }
        binding.companyList.adapter = adapter

        lifecycleScope.launch {
            viewModel.companyList.flowWithLifecycle(lifecycle).collect { companyList ->
                adapter.submitList(companyList)
            }
        }
    }
}