package com.example.bookingapp.domain.repositories_interface

import com.example.bookingapp.domain.entities.Place
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface PlaceRepository {

    fun getPlacesInfoByCompanyName(companyName: String): Flow<List<Place>>

    fun getPlaceInfoById(id: String): StateFlow<Place>
}