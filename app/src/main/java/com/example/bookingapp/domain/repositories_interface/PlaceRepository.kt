package com.example.bookingapp.domain.repositories_interface

import com.example.bookingapp.domain.entities.Place
import kotlinx.coroutines.flow.StateFlow

interface PlaceRepository {

    fun getPlacesInfoByCompanyId(companyId: String): StateFlow<List<Place>>

    fun getPlaceInfoById(id: String): StateFlow<Place>
}