package com.example.bookingapp.app.fragments.places

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapp.databinding.CardPlaceBinding
import com.example.bookingapp.domain.entities.Place
import com.google.android.material.chip.Chip

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
        if (data.name == "")
            placeName.visibility = View.GONE
        else
            placeName.text = data.name
        placeFeatures.removeAllViews()
        for (feature in item.features) {
            val chip = Chip(placeFeatures.context)
            chip.isClickable = false
            chip.text = feature
            placeFeatures.addView(chip)
        }
    }
}