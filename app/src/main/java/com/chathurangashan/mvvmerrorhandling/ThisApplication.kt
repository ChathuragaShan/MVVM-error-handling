package com.chathurangashan.mvvmerrorhandling

import android.app.Application
import com.chathurangashan.mvvmerrorhandling.data.enums.BuildType
import com.chathurangashan.mvvmerrorhandling.di.DaggerApplicationComponent
import com.chathurangashan.mvvmerrorhandling.di.InjectorProvider
import com.facebook.stetho.Stetho

class ThisApplication : Application(), InjectorProvider {

    companion object {
        val buildType: BuildType = BuildType.RELEASE
    }

    override val component by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
    }
}