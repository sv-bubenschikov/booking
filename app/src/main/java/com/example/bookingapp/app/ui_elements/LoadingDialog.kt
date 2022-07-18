package com.example.bookingapp.app.ui_elements

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.bookingapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class LoadingDialog(private val inflater: LayoutInflater, val context: Context) {
    private lateinit var isDialog: AlertDialog
    fun startLoading() {
        val dialogView = inflater.inflate(R.layout.loading_progress_bar, null)
        val builder = MaterialAlertDialogBuilder(context)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isDialog = builder.create()
        isDialog.show()
    }

    fun dismiss() {
        isDialog.dismiss()
    }
}