package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.Booking
import com.example.bookingapp.domain.entities.Company
import com.example.bookingapp.domain.entities.Day
import com.example.bookingapp.domain.entities.Period
import com.example.bookingapp.domain.repositories_interface.DateRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import javax.inject.Inject

class DateRepositoryImpl @Inject constructor(
    private val database: DatabaseReference
) : DateRepository {

    private val today = DateTime.now().withTimeAtStartOfDay()

    private val fakeDays = listOf(
        Day(0, today.millis),
        Day(1, today.plusDays(1).millis),
        Day(2, today.plusDays(2).millis),
        Day(3, today.plusDays(3).millis),
        Day(4, today.plusDays(4).millis),
        Day(5, today.plusDays(5).millis),
        Day(6, today.plusDays(6).millis),
        Day(7, today.plusDays(7).millis),
        Day(8, today.plusDays(8).millis),
    )

    override fun getDaysInfoByPlaceId(): StateFlow<List<Day>> {
       return MutableStateFlow(fakeDays)
    }

    override fun getPeriodsByDayId(dayId: Int)= callbackFlow {
        val periods = database.child("Places").child("GeneratedPlaceId").child("periods")

        val listener = periods.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                launch {
                    send(snapshot.children.mapNotNull { it.getValue(Period::class.java) })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        awaitClose { periods.removeEventListener(listener) }
    }



    val bookings = callbackFlow {
        val periods = database.child("Bookings")

        val listener = periods.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                launch {
                    send(snapshot.children.mapNotNull {
                        it.getValue(Booking::class.java)
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        awaitClose { periods.removeEventListener(listener) }
    }
}