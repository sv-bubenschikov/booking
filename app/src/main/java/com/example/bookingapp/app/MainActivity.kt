package com.example.bookingapp.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bookingapp.R
import com.example.bookingapp.app.fragments.booking.BookingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setBookingFragment()
    }

    private fun setBookingFragment(){
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view, BookingFragment())
            .commit()
    }
}