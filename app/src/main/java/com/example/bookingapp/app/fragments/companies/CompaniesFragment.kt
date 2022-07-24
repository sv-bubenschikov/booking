package com.example.bookingapp.app.fragments.companies

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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
import com.example.bookingapp.app.fragments.places.PlacesFragment.Companion.COMPANY_ID
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

        hostViewModel.setActionButtonVisible(false)

        context.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)

                val searchView = menu.findItem(R.id.menu_search).actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        viewModel.onNewSearchQuery(query)
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        viewModel.onNewSearchQuery(newText)
                        return true
                    }
                })
                viewModel.searchQuery.value.takeIf { it.isNotEmpty() }?.let { query ->
                    // TODO: #36
                    searchView.setQuery(query, false)
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem) = false
        }, this, Lifecycle.State.RESUMED)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCompaniesBinding.inflate(inflater, container, false)

        val adapter = CompaniesListAdapter { company ->
            val arg = Bundle().apply {
                putString(COMPANY_ID, company.name)
            }
            findNavController().navigate(R.id.action_companiesFragment_to_placesFragment, arg)
        }

        binding.companyList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.companyList
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { companyList ->
                    adapter.submitList(companyList)
                }
        }

        return binding.root
    }
}