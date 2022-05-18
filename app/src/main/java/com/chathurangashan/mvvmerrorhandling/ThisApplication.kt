package com.chathurangashan.mvvmerrorhandling

import android.app.Application
import com.chathurangashan.mvvmerrorhandling.data.enums.BuildType
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ThisApplication : Application() {

    companion object {
        val buildType: BuildType = BuildType.RELEASE
    }

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
    }
}