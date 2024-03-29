package com.example.bookingapp.app.fragments.companies

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookingapp.databinding.CompaniesItemBinding
import com.example.bookingapp.domain.entities.Company

class CompaniesListViewHolder(
    private val binding: CompaniesItemBinding,
    private val onItemClicked: (Company) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var data: Company

    init {
        itemView.setOnClickListener {
            onItemClicked(data)
        }
    }

    fun bind(item: Company) = with(binding) {
        data = item
        companyName.text = data.name
        val glide = Glide.with(itemView.context)
        glide.clear(companyLogo)
        glide.load(data.logoUrl)
            .into(companyLogo)
        Unit
    }
}