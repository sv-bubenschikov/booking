package com.example.bookingapp.app.fragments.places

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingapp.databinding.CardPlaceBinding
import com.example.bookingapp.domain.entities.Place

class PlaceAdapter(private val clickListener: () -> Unit) :
    RecyclerView.Adapter<PlaceAdapter.PlaceHolder>() {

    private var places: List<Place> = emptyList()

    class PlaceHolder(val viewBinding: CardPlaceBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val binding = CardPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        val item = places[position]
        with(holder.viewBinding) {
            placeType.text = item.type
            placeName.text = item.name
            placeFloor.text = "${item.floor} этаж"
        }

        holder.itemView.setOnClickListener {
            clickListener.invoke()
        }
    }

    override fun getItemCount(): Int = places.size

    @SuppressLint("NotifyDataSetChanged")
    fun setPlaces(places: List<Place>) {
        this.places = places
        notifyDataSetChanged()
    }
}