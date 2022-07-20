package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.*
import com.example.bookingapp.domain.repositories_interface.UserRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {
        override fun getUserInfoById(id: Int): User {
        TODO("Not yet implemented")
    }

    override suspend fun registerUser(email: String, password: String): AuthResult {
        val mAuth = FirebaseAuth.getInstance()
        mAuth.signOut()
        return mAuth.createUserWithEmailAndPassword(email, password)
            .await()
    }

    override suspend fun updateUserInfo(user: User): Void {
        return FirebaseDatabase.getInstance().reference
            .child("Users")
            .child(user.id)
            .setValue(user)
            .await()
    }

    override suspend fun deleteUserInfoById(id: String): Void {
        return FirebaseDatabase.getInstance().reference
            .child("Users")
            .child(id)
            .removeValue()
            .await()
    }

    override suspend fun signInUser(email: String, password: String): AuthResult {
        val mAuth = FirebaseAuth.getInstance()
        return mAuth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun signInAsGuest(): AuthResult {
        val mAuth = FirebaseAuth.getInstance()
        return mAuth.signInAnonymously()
            .await()
    }

    override fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

    override suspend fun updateCurrentUserRef() {
        val userRef = FirebaseAuth.getInstance().currentUser
        try {
            userRef?.reload()?.await()
        }
        catch (ex: FirebaseAuthInvalidUserException) {
            return
        }
    }

    override fun getCurrentUserRef(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }
}