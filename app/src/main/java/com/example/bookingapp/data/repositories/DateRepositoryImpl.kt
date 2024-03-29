package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.Booking
import com.example.bookingapp.domain.entities.Day
import com.example.bookingapp.domain.entities.Period
import com.example.bookingapp.domain.repositories_interface.DateRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import javax.inject.Inject

class DateRepositoryImpl @Inject constructor(
    private val database: DatabaseReference
) : DateRepository {

    /**
     * Генерирует список дней на каждый вызов, чтобы избежать проблем на гронице дней
     */
    override fun getDaysInfoByPlaceId() = flowOf(DateTime.now().withTimeAtStartOfDay())
        .map { today ->
            listOf(
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
        }

    override fun getPeriodsByDayId(dayId: Int, placeId: String) = callbackFlow {
        val periods = database.child("Places").child(placeId).child("periods")

        val listener = periods.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                launch {
                    send(snapshot.children.mapNotNull { it.getValue(Period::class.java) })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                cancel("Unable to update periods list", error.toException())
            }
        })
        awaitClose { periods.removeEventListener(listener) }
    }

    override fun getBookingPeriodsByDate(date: Long, placeId: String) = callbackFlow {
        val bookings = database.child("Bookings")

        val listener = bookings.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                launch {
                    send(snapshot.children.mapNotNull {
                        it.getValue(Booking::class.java)
                    }.filter { booking ->
                        (booking.bookingDate == date) and (booking.placeId == placeId)
                    }.map { b ->
                        Period(b.startTime, b.endTime)
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                cancel("Unable to update booking periods list", error.toException())
            }
        })
        awaitClose { bookings.removeEventListener(listener) }
    }
}