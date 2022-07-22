package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.Booking
import com.example.bookingapp.domain.entities.Day
import com.example.bookingapp.domain.entities.Period
import com.example.bookingapp.domain.repositories_interface.DateRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.joda.time.DateTime
import javax.inject.Inject

class DateRepositoryImpl @Inject constructor() : DateRepository {

    private val today = DateTime.now().withTimeAtStartOfDay()
    var periods: List<Period> = emptyList()
    var bookings: List<Booking> = emptyList()

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

    private var fakePeriods = listOf(
        Period(7200000, 9000000),
        Period(32400000, 34200000),
        Period(54000000, 55800000),
        Period(32400000, 34200000),
        Period(54000000, 55800000),
        Period(9000000, 10800000),
        Period(10800000, 12600000),
    )

    override fun getDaysInfoByPlaceId(): StateFlow<List<Day>> {
        setupDB()
        return MutableStateFlow(fakeDays)
    }

    override fun getPeriodsByDayId(dayId: Int): StateFlow<List<Period>> {
       // val res: ArrayList<Period> = periods
        for (period in bookings.map { booking -> Period(booking.startTime, booking.endTime) }) {
           // if (periods.contains(period)) res.remove(period)
        }
        return MutableStateFlow(fakePeriods)//MutableStateFlow(res)
    }

    private fun setupDB() {
        val database = Firebase.database

        val pRef = database.getReference("Places")
        //val bRef = database.getReference("Bookings")

        pRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val place = snapshot.children.first()
                periods = place.child("periods").getValue<List<Period>>() ?: emptyList()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
/*
        bRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val b = snapshot.child("GeneratedBookingId").getValue<Booking>()
                //bookings = snapshot.getValue<List<Booking>>() ?: emptyList()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })*/
    }
}