package com.example.bookingapp.domain.repositories_interface

import com.example.bookingapp.domain.entities.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserInfoById(id: String): Flow<User>

    suspend fun registerUser(email: String, password: String): AuthResult

    suspend fun signInUser(email: String, password: String): AuthResult

    suspend fun updateUserInfo(user: User): Void

    suspend fun signInAsGuest(): AuthResult

    suspend fun logout()

    fun getCurrentUserRef(): Flow<FirebaseUser?>

    fun getCurrentUser() : FirebaseUser?
}