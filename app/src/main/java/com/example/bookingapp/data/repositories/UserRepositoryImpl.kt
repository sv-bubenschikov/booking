package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.User
import com.example.bookingapp.domain.repositories_interface.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {
    override fun getUserInfoById(id: Int): User {
        TODO("Not yet implemented")
    }

    override suspend fun registerUser(email: String, password: String): AuthResult? {
        email.apply { if(trim().isEmpty()) return null }
        password.apply { if(trim().isEmpty()) return null }
        val mAuth = FirebaseAuth.getInstance()
        mAuth.signOut()
        return try {
            mAuth.createUserWithEmailAndPassword(email, password)
                .await()
        } catch (ex: Exception) {
            null
        }
    }

    override suspend fun updateUserInfo(user: User): Boolean {
        val userMap = HashMap<String, Any>()
        userMap["id"] = user.id
        userMap["name"] = user.name
        userMap["email"] = user.email
        return try {
            FirebaseDatabase.getInstance().reference
                .child("Users")
                .child(user.id)
                .setValue(userMap)
                .await()
            true
        } catch (ex: Exception) {
            false
        }
    }

    override suspend fun signInUser(email: String, password: String): AuthResult? {
        email.apply { if(trim().isEmpty()) return null }
        password.apply { if(trim().isEmpty()) return null }
        val mAuth = FirebaseAuth.getInstance()
        return try {
            mAuth.signInWithEmailAndPassword(email, password)
                .await()
        } catch (ex: Exception) {
            null
        }
    }

    override suspend fun signInAsGuest(): AuthResult? {
        val mAuth = FirebaseAuth.getInstance()
        return try {
            mAuth.signInAnonymously()
                .await()
        } catch (ex: Exception) {
            null
        }
    }

    override fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }
}