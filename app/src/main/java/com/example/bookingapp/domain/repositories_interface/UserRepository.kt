package com.example.bookingapp.domain.repositories_interface

import com.example.bookingapp.domain.entities.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface UserRepository {

    fun getUserInfoById(id: Int): User

    // Тут должнен был быть тип Completable из реактивного программирования, но т.к. будем использовать
    // coroutines, поставил пока что тип Boolean, потому что с coroutines еще не работал.
    // Потом нужно будет все на многопоточность перенести.
    fun registerUser(email: String, password: String): Task<AuthResult>

    fun signInUser(email: String, password: String): Task<AuthResult>

    fun updateUserInfo(user: User): Task<Void>

    fun signInAsGuest(): Task<AuthResult>
}