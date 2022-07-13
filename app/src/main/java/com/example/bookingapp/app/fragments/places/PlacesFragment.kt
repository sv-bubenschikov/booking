package com.example.bookingapp.app.fragments.places

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bookingapp.R
import com.example.bookingapp.databinding.FragmentCompaniesBinding
import com.example.bookingapp.databinding.FragmentPlacesBinding
import com.example.bookingapp.domain.entities.Place
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlacesFragment : Fragment(R.layout.fragment_places) {

    private val viewModel: PlacesViewModel by viewModels()
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

            // Пока что добавил заглушку, чтобы можно было проверить верстку
            val attributes = listOf("1 этаж", "2 этаж", "3 этаж", "Переговорка", "4 этаж", "5 этаж")
            for (attr in attributes) {
                val chip =
                    layoutInflater.inflate(R.layout.chip_filter_layout, chipsFilter, false) as Chip
                chip.text = attr
                chipsFilter.addView(chip)
            }
        }

        //Т.к id компании еще не пересылается из CompaniesFragment, поставил id-заглушку
        showPlacesInfo(viewModel.getPlacesInfo(1))
    }

    private fun showPlacesInfo(places: List<Place>) {
        placeAdapter.setPlaces(places)
    }
}