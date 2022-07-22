package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.Place
import com.example.bookingapp.domain.repositories_interface.PlaceRepository
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor() : PlaceRepository {
    private val places = listOf(
        Place(1, "Алиса", "Переговорка", "2 этаж", 6, emptyList()),
        Place(2, "Hawaii", "Переговорка", "3 этаж", 6, emptyList()),
        Place(3, "", "Бильярдный стол", "3 этаж", 2, emptyList()),
        Place(4, "", "Бильярдный стол", "2 этаж", 2, emptyList()),
        Place(5, "ChinaTown", "Переговорка", "3 этаж", 10, emptyList())
    )


    override fun getPlacesInfoByCompanyId(companyId: Int): List<Place> {
        return places
    }

    override fun getPlaceInfoById(id: Int): Place {
        TODO("Not yet implemented")
    }
}