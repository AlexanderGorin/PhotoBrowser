package com.alexandergorin.photobrowser

import android.app.Application
import com.alexandergorin.photobrowser.di.AppComponent
import com.alexandergorin.photobrowser.di.DaggerAppComponent

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}