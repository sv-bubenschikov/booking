package com.example.bookingapp.app.fragments.companies

import androidx.recyclerview.widget.DiffUtil
import com.example.bookingapp.domain.entities.Company

object CompanyDiffCallBack : DiffUtil.ItemCallback<Company>()  {
    override fun areItemsTheSame(oldItem: Company, newItem: Company) =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Company, newItem: Company) =
        oldItem == newItem
}