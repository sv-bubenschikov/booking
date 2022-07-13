package com.example.bookingapp.app.fragments.places

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bookingapp.R
import com.example.bookingapp.databinding.FragmentCompaniesBinding
import com.example.bookingapp.databinding.FragmentPlacesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlacesFragment : Fragment(R.layout.fragment_places) {

    private lateinit var viewModel: PlacesViewModel
    private lateinit var placeAdapter: PlaceAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentPlacesBinding.bind(view)

        placeAdapter = PlaceAdapter {
            findNavController(view).navigate(R.id.action_placesFragment_to_bookingPlaceFragment)
        }
        with(binding) {
            recyclePlaces.apply {
                layoutManager = GridLayoutManager(this.context, 2)
                adapter = placeAdapter
            }
        }
    }
}