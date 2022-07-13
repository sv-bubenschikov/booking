package com.example.bookingapp.data.repositories

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.bookingapp.app.fragments.ui_elements.LoadingDialog
import com.example.bookingapp.domain.entities.User
import com.example.bookingapp.domain.repositories_interface.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {
    override fun getUserInfoById(id: Int): User {
        TODO("Not yet implemented")
    }

    override fun registerUser(user: User): Boolean {
        val mAuth = FirebaseAuth.getInstance()
        var isSuccessfulRegister = false
        var isSuccessfulSavedUserInfo = false
        if(user.email.isEmpty() || user.password.isEmpty() || user.name.isEmpty())
            return isSuccessfulRegister
        //val loadingDialog = LoadingDialog(inflater, context!!) как от сюда запустить loadingBar?
        mAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
            if(task.isSuccessful) {
                isSuccessfulRegister = true
                isSuccessfulSavedUserInfo = saveUserInfo(mAuth, user)
            }
            else {
                //progressBar.dismiss()
                val msg = task.exception.toString().substringAfter(':')
                //Toast.makeText(context, "Error: $msg", Toast.LENGTH_LONG).show() как от сюда показать toast?
                mAuth.signOut()
            }
        }

        return isSuccessfulRegister
    }

    private fun saveUserInfo(mAuth: FirebaseAuth, userModel: User): Boolean {
        var isSuccessful = false
        val currentUserID = mAuth.currentUser!!.uid
        val userRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")
        val userMap = HashMap<String, Any>()
        userMap["id"] = currentUserID
        userMap["name"] = userModel.name
        userMap["email"] = userModel.email

//        val bundle = Bundle()
//        bundle.putString("password", userModel.password)
//        bundle.putString("email", userModel.email)

        userRef.child(currentUserID).setValue(userMap)
            .addOnCompleteListener {
                    task ->
                if(task.isSuccessful) {
                    isSuccessful = true
                    //findNavController().navigate(R.id.action_navigation_sign_up_to_navigation_verify_email, bundle)
                }
                else {
                    val msgError = task.exception.toString().substringAfter(':')
                    //Toast.makeText(context, "Error: $msgError", Toast.LENGTH_LONG).show()
                    mAuth.signOut()
                }
                //progressBar.dismiss()
            }
        return isSuccessful
    }

    override fun updateUserInfo(user: User): Boolean {
        TODO("Not yet implemented")
    }

    override fun signInUser(user: User): Boolean {
        val mAuth = FirebaseAuth.getInstance()
        var isSuccessful = false
        if(user.email.isEmpty() || user.password.isEmpty())
            return isSuccessful
        mAuth.signInWithEmailAndPassword(user.email, user.password).addOnCompleteListener {
            if(it.isSuccessful) {
                isSuccessful = true
            }
            else {

            }
        }

        return isSuccessful
    }

    override fun signInAsGuest() {
        FirebaseAuth.getInstance().signInAnonymously()
    }
}