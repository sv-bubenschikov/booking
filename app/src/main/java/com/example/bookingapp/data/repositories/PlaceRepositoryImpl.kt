package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.Place
import com.example.bookingapp.domain.repositories_interface.PlaceRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val database: DatabaseReference
) : PlaceRepository {

    override fun getPlacesInfoByCompanyName(companyName: String) = callbackFlow {
        val places = database.child("Places")
        // TODO #42
        val listener = places.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                launch {
                    send(snapshot.children.mapNotNull {
                        it.getValue(Place::class.java)
                    }.filter { place -> place.company == companyName})
                }
            }

            override fun onCancelled(error: DatabaseError) {
                cancel("Unable to update places list", error.toException())
            }
        })
        awaitClose { places.removeEventListener(listener) }
    }

    override fun getPlaceInfoById(id: String): StateFlow<Place> {
        TODO("Not yet implemented")
    }
}