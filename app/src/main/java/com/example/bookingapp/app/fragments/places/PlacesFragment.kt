package com.example.bookingapp.app.fragments.places

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import com.example.bookingapp.R
import com.example.bookingapp.databinding.FragmentCompaniesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlacesFragment : Fragment(R.layout.fragment_places) {

    private lateinit var viewModel: PlacesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCompaniesBinding.bind(view)

        binding.buttonNextFragment.setOnClickListener {
            findNavController(view).navigate(R.id.action_placesFragment_to_bookingPlaceFragment)
        }
    }
}