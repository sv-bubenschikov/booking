package com.example.bookingapp.app.fragments.companies

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.app.HostViewModel
import com.example.bookingapp.app.fragments.booking.BookingListAdapter
import com.example.bookingapp.app.fragments.deialts.BookingDetailsFragment
import com.example.bookingapp.app.fragments.places.PlacesFragment.Companion.COMPANY_ID
import com.example.bookingapp.app.fragments.places.PlacesFragment.Companion.COMPANY_TITLE
import com.example.bookingapp.databinding.FragmentCompaniesBinding
import com.example.bookingapp.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompaniesFragment : Fragment(R.layout.fragment_companies) {

    private val hostViewModel: HostViewModel by activityViewModels()
    private val viewModel: CompaniesViewModel by viewModels()

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
                        findNavController().navigate(R.id.action_companiesFragment_to_placesFragment)
                        true
                    }
                    else -> false
                }
            }
        }, this, Lifecycle.State.RESUMED)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCompaniesBinding.inflate(inflater, container, false)
        hostViewModel.setActionButtonVisible(false)

        val adapter = CompaniesListAdapter { company ->
            val arg = Bundle().apply {
                putString(COMPANY_ID, company.name)
            }
            findNavController().navigate(R.id.action_companiesFragment_to_placesFragment, arg)
        }

        binding.companyList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.companyList.flowWithLifecycle(viewLifecycleOwner.lifecycle).collect { companyList ->
                adapter.submitList(companyList)
            }
        }

        binding.companyList.adapter = adapter

        binding.txtSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(text: String?): Boolean {
                text?.let {
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.searchText.emit(text)
                    }
                }

                return false
            }

            override fun onQueryTextSubmit(text: String?): Boolean {
                TODO("Not yet implemented")
            }
        })

        return binding.root
    }
}