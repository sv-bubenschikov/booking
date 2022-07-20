package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.Company
import com.example.bookingapp.domain.repositories_interface.CompanyRepository
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor() : CompanyRepository {
    override fun getCompaniesInfo(): List<Company> {
        TODO("Not yet implemented")
    }

    override fun getCompanyInfoById(id: Int): Company {
        TODO("Not yet implemented")
    }

    override suspend fun createCompany(company: Company): Void {
        val postRef = FirebaseDatabase.getInstance().reference
            .child("Companies")
        val newPostRef = postRef
            .push()
        company.id = newPostRef.key!!
        return newPostRef
            .setValue(company)
            .await()
    }

    override suspend fun deleteCompanyById(id: String): Void {
        return FirebaseDatabase.getInstance().reference
            .child("Companies")
            .child(id)
            .removeValue()
            .await()
    }
}