package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.Place
import com.example.bookingapp.domain.repositories_interface.PlaceRepository
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor() : PlaceRepository {
    override fun getPlacesInfoByCompanyId(companyId: Int): List<Place> {
        TODO("Not yet implemented")
    }

    override fun getPlaceInfoById(id: Int): Place {
        TODO("Not yet implemented")
    }
}