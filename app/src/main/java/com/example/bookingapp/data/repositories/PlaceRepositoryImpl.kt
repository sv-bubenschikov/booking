package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.Place
import com.example.bookingapp.domain.repositories_interface.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor() : PlaceRepository {
    private val places = MutableStateFlow(
        listOf(
            Place("Алиса", "Переговорка", 2, 6, "company id", emptyList()),
            Place("Hawaii", "Переговорка", 3, 6, "company id", emptyList()),
            Place("", "Бильярдный стол", 3, 2,"company id", emptyList()),
            Place("", "Бильярдный стол", 2, 2,"company id", emptyList()),
            Place("ChinaTown", "Переговорка", 3, 10, "company id", emptyList())
        )
    )


    override fun getPlacesInfoByCompanyId(companyId: Int): StateFlow<List<Place>> {
        return places
    }

    override fun getPlaceInfoById(id: Int): StateFlow<Place> {
        TODO("Not yet implemented")
    }

    override suspend fun createPlace(place: Place): Void {
        val postRef = FirebaseDatabase.getInstance().reference
            .child("Places")
        val newPostRef = postRef
            .push()
        place.id = newPostRef.key!!
        return newPostRef
            .setValue(place)
            .await()
    }

    override suspend fun deletePlaceById(id: String): Void {
        return FirebaseDatabase.getInstance().reference
            .child("Places")
            .child(id)
            .removeValue()
            .await()
    }
}