package com.example.bookingapp.data.repositories

import com.example.bookingapp.domain.entities.User
import com.example.bookingapp.domain.repositories_interface.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {
    override fun getUserInfoById(id: Int): User {
        TODO("Not yet implemented")
    }

    override fun registerUser(email: String, password: String): Task<AuthResult> {
        val mAuth = FirebaseAuth.getInstance()
        return mAuth.createUserWithEmailAndPassword(email, password)
    }

    override fun updateUserInfo(user: User): Task<Void> {
        val mAuth = FirebaseAuth.getInstance()
        val currentUserID = mAuth.currentUser!!.uid
        val userRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")
        val userMap = HashMap<String, Any>()
        userMap["id"] = currentUserID
        userMap["name"] = user.name
        userMap["email"] = user.email
        return userRef.child(currentUserID).setValue(userMap)
    }

    override fun signInUser(email: String, password: String): Task<AuthResult> {
        val mAuth = FirebaseAuth.getInstance()
        return mAuth.signInWithEmailAndPassword(email, password)
    }

    override fun signInAsGuest():  Task<AuthResult> {
        return FirebaseAuth.getInstance().signInAnonymously()
    }
}