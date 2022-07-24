package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.Company
import com.example.bookingapp.domain.repositories_interface.CompanyRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val database: DatabaseReference
) : CompanyRepository {

    override fun getCompanies() = callbackFlow {
        val companies = database.child("Companies")
        val listener = companies.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                launch {
                    send(snapshot.children.mapNotNull { it.getValue(Company::class.java) })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                cancel("Unable to update companies list", error.toException())
            }
        })
        awaitClose { companies.removeEventListener(listener) }
    }

    override fun getCompanyInfoById(id: String): Company {
        TODO("Not yet implemented")
    }
}