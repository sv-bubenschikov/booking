package com.example.bookingapp.domain.repositories_interface

import com.example.bookingapp.domain.entities.Place
import kotlinx.coroutines.flow.StateFlow

interface PlaceRepository {

    fun getPlacesInfoByCompanyId(companyId: Int): StateFlow<List<Place>>

    fun getPlaceInfoById(id: Int): StateFlow<Place>
}