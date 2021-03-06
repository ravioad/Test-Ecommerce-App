package com.ravikumar.testapp.misc

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.ravikumar.testapp.BuildConfig
import com.ravikumar.testapp.R
import com.ravikumar.testapp.activities.OnBoardingActivity
import java.math.BigDecimal
import java.math.RoundingMode


fun OnBoardingActivity.saveSeenState() {
    val preferences: SharedPreferences =
        applicationContext.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = preferences.edit()
    editor.putBoolean(Constants.onBoardingSeenState, true)
    editor.apply()
}

fun OnBoardingActivity.checkSeenState(): Boolean {
    val preferences: SharedPreferences =
        applicationContext.getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
    return preferences.getBoolean(Constants.onBoardingSeenState, false)
}

fun Activity.setWhiteStatusBarColor() {
    val window = window
    val decorView = window.decorView
    val wic = WindowInsetsControllerCompat(window, decorView)
    wic.isAppearanceLightStatusBars = true // true or false as desired.
    // And then you can set any background color to the status bar.
    window.statusBarColor = ContextCompat.getColor(this, R.color.backgroundColor)
}

fun String.printLog(text: Any?) {
    if (BuildConfig.DEBUG) {
        Log.e(this, if (text !is String) text.toString() else text)
    }
}

fun String.showToastShort(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun Double.roundTo2Places(): Double {
    return BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN).toDouble()
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}
