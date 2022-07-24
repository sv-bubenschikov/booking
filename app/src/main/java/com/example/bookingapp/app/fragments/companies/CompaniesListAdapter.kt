package com.example.bookingapp.app.fragments.companies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.bookingapp.databinding.CompaniesItemBinding
import com.example.bookingapp.domain.entities.Company

class CompaniesListAdapter  (
    private val onItemClicked: (Company) -> Unit
) : ListAdapter<Company, CompaniesListViewHolder>(CompanyDiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context).let { inflater ->
            CompaniesListViewHolder(
                CompaniesItemBinding.inflate(inflater, parent, false),
                onItemClicked
            )
        }

    override fun onBindViewHolder(holder: CompaniesListViewHolder, position: Int) =
        holder.bind(getItem(position))
}