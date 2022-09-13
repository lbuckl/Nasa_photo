package com.vados.nasa_photo

import android.app.Application

class MyApp:Application() {
    companion object {
        private var myApp: MyApp? = null
        fun getMyApp() = myApp!!
    }

    override fun onCreate() {
        super.onCreate()
        myApp = this
    }
}