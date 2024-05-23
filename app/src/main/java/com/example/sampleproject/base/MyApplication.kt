package com.example.sampleproject.base

import android.R
import android.app.Application
import android.graphics.Typeface


/**
 * Application class is a base class for maintaining global application state
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Set the default typeface for all TextViews
        val customTypeface = Typeface.createFromAsset(assets, "fonts/oswald.ttf")
        setDefaultTypeface(customTypeface)
    }

    private fun setDefaultTypeface(typeface: Typeface) {
        try {
            val field = Typeface::class.java.getDeclaredField("DEFAULT")
            field.isAccessible = true
            field.set(null, typeface)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}