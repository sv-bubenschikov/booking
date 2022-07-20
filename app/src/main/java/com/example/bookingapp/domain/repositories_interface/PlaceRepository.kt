package com.example.bookingapp.domain.repositories_interface

import com.example.bookingapp.domain.entities.Place

interface PlaceRepository {

    fun getPlacesInfoByCompanyId(companyId: Int): List<Place>

    fun getPlaceInfoById(id: Int): Place

    suspend fun createPlace(place: Place): Void

    suspend fun deletePlaceById(id: String): Void
}