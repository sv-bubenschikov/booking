package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.Booking
import com.example.bookingapp.domain.repositories_interface.BookingRepository
import com.google.firebase.database.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BookingRepositoryImpl @Inject constructor(
    private val database: DatabaseReference
) : BookingRepository {

    override fun getBookingsByUserId(userId: String) = callbackFlow {
        val bookings = database.child("Bookings")
        val listener = bookings.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                launch {
                    send(snapshot.children.mapNotNull { it.getValue(Booking::class.java) }.filter {
                        it.members.contains(userId)
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                cancel("Unable to update bookings list", error.toException())
            }
        })
        awaitClose { bookings.removeEventListener(listener) }
    }

    override fun getBookingInfoById(id: String) = callbackFlow {
        val booking = database.child("Bookings").child(id)
        val listener = booking.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                launch {
                    snapshot.getValue(Booking::class.java)?.let { send(it) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                cancel("Unable to update booking", error.toException())
            }
        })
        awaitClose { booking.removeEventListener(listener) }
    }

    override suspend fun createBooking(booking: Booking) {
        FirebaseDatabase.getInstance().reference
            .child("Bookings")
            .push()
            .setValue(booking)
            .await()
    }

    override suspend fun deleteBookingById(id: String) {
        FirebaseDatabase.getInstance().reference
            .child("Bookings")
            .child(id)
            .removeValue()
            .await()
    }
}