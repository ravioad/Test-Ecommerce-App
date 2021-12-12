package com.ravikumar.testapp.activities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ravikumar.testapp.R
import com.ravikumar.testapp.misc.Constants

abstract class MyBaseActivity : AppCompatActivity() {
    private lateinit var context: Activity

    fun addCommonUtils(context: Activity) {
        this.context = context
    }

    fun showAlert(message: String?) {
        when (message) {
            Constants.timeOutError -> {
                showCustomAlert(
                    resources.getString(R.string.connectionError),
                    resources.getString(R.string.connectionErrorMessage)
                )
            }
            Constants.unknownError -> {
                showCustomAlert(
                    resources.getString(R.string.error),
                    resources.getString(R.string.unknownError)
                )
            }
            else -> {
                showCustomAlert(
                    resources.getString(R.string.connectionError),
                    resources.getString(R.string.connectionErrorMessage)
                )
            }
        }
    }

    fun showCustomAlert(title: String, message: String) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
                dialog.dismiss()
            }.show()
    }
}