package com.example.bookingapp.data.repositories

import android.util.Log
import com.example.bookingapp.domain.entities.User
import com.example.bookingapp.domain.repositories_interface.UserRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val database: DatabaseReference
) : UserRepository {

    override fun getUserInfoById(id: String): Flow<User> {
        return callbackFlow {
            val ref = database.child("Users").child(id)
            val listener = ref.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    cancel("Unable to get user by id", error.toException())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    launch {
                        snapshot.getValue(User::class.java)?.let { send(it) }
                    }
                }
            })

            awaitClose { ref.removeEventListener(listener) }
        }
    }

    override suspend fun registerUser(email: String, password: String): AuthResult {
        val mAuth = FirebaseAuth.getInstance()
        mAuth.signOut()
        return mAuth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun updateUserInfo(user: User): Void {
        return database
            .child("Users")
            .child(user.id)
            .setValue(user)
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

    override suspend fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

    override suspend fun updateCurrentUserRef() {
        val userRef = getCurrentUserRef()
        try {
            userRef?.reload()?.await()
        } catch (ex: FirebaseAuthInvalidUserException) {
            Log.w("AUTH", ex)
        }
    }

    override suspend fun getCurrentUserRef(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }
}