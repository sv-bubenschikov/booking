package com.example.bookingapp.domain.repositories_interface

import com.example.bookingapp.domain.entities.User

interface UserRepository {

    fun getUserInfoById(id: Int): User

    // Тут должнен был быть тип Completable из реактивного программирования, но т.к. будем использовать
    // coroutines, поставил пока что тип Boolean, потому что с coroutines еще не работал.
    // Потом нужно будет все на многопоточность перенести.
    fun registerUser(user: User): Boolean

    fun signInUser(user: User): Boolean

    fun updateUserInfo(user: User): Boolean

    fun signInAsGuest()
}