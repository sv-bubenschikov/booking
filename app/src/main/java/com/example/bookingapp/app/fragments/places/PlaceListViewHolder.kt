package com.example.bookingapp.app.fragments.places

import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapp.databinding.CardPlaceBinding
import com.example.bookingapp.domain.entities.Place

class PlaceListViewHolder(
    private val binding: CardPlaceBinding,
    private val onItemClicked: (Place) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var data: Place

    init {
        itemView.setOnClickListener {
            onItemClicked(data)
        }
    }

    fun bind(item: Place) = with(binding) {
        data = item
        placeType.text = data.type
        placeName.text = data.name
        placeFloor.text = data.floor
    }
}