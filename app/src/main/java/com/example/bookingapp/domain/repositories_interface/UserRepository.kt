package com.example.bookingapp.domain.repositories_interface

import com.example.bookingapp.domain.entities.Company
import com.example.bookingapp.domain.entities.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {

    fun getUserInfoById(id: String): Flow<User>

    // Тут должнен был быть тип Completable из реактивного программирования, но т.к. будем использовать
    // coroutines, поставил пока что тип Boolean, потому что с coroutines еще не работал.
    // Потом нужно будет все на многопоточность перенести.
    suspend fun registerUser(email: String, password: String): AuthResult

    suspend fun signInUser(email: String, password: String): AuthResult

    suspend fun updateUserInfo(user: User): Void

    suspend fun signInAsGuest(): AuthResult

    fun logout()

    suspend fun updateCurrentUserRef()

    fun getCurrentUserRef(): FirebaseUser?
}