package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.Place
import com.example.bookingapp.domain.repositories_interface.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor() : PlaceRepository {
    private val places = MutableStateFlow(
        listOf(
            Place(1, 1, "Алиса", "Переговорка", "2 этаж", 6, emptyList()),
            Place(2, 1, "Hawaii", "Переговорка", "3 этаж", 6, emptyList()),
            Place(3, 2, "", "Бильярдный стол", "3 этаж", 2, emptyList()),
            Place(4, 1, "", "Бильярдный стол", "2 этаж", 2, emptyList()),
            Place(5, 2, "ChinaTown", "Переговорка", "3 этаж", 10, emptyList())
        )
    )

    override fun getPlacesInfoByCompanyId(companyId: Int): StateFlow<List<Place>> {
        return places
    }

    override fun getPlaceInfoById(id: Int): StateFlow<Place> {
        TODO("Not yet implemented")
    }
}