package com.example.bookingapp.app.fragments.places

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.bookingapp.databinding.CardPlaceBinding
import com.example.bookingapp.domain.entities.Place

class PlaceListAdapter(
    private val onItemClicked: (Place) -> Unit
) : ListAdapter<Place, PlaceListViewHolder>(PlaceDiffCallback) {

    private val filters: ArrayList<String> = ArrayList()
    private var places: List<Place> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceListViewHolder {
        val binding = CardPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceListViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: PlaceListViewHolder, position: Int) =
        holder.bind(getItem(position))

    override fun submitList(list: MutableList<Place>?) {
        super.submitList(list)
        places = list!!.toList()
    }

    fun addFilter(filter: String) {
        filters.add(filter)
    }

    fun removeFilter(filter: String) {
        filters.remove(filter)
    }

    fun filterPlaces() {
        super.submitList(places.filter { place -> place.features.containsAll(filters) })
    }
}