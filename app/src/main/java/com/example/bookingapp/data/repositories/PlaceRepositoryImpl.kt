package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.Place
import com.example.bookingapp.domain.repositories_interface.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor() : PlaceRepository {
    private val places = MutableStateFlow(
        listOf(
            Place("Тензор", listOf("4 места", "3 этаж"), "Алиса", "Переговорка", emptyList()),
            Place("Тензор", listOf("4 места", "3 этаж"), "Алиса", "Переговорка", emptyList()),
            Place("Тензор", listOf("4 места", "3 этаж"), "Алиса", "Переговорка", emptyList()),
            Place("Тензор", listOf("4 места", "3 этаж"), "Алиса", "Переговорка", emptyList()),
            Place("Тензор", listOf("4 места", "3 этаж"), "Алиса", "Переговорка", emptyList()),
        )
    )

    override fun getPlacesInfoByCompanyId(companyId: Int): StateFlow<List<Place>> {
        return places
    }

    override fun getPlaceInfoById(id: Int): StateFlow<Place> {
        TODO("Not yet implemented")
    }
}