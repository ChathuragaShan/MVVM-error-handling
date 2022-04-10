package com.chathurangashan.mvvmerrorhandling.di

import android.content.Context
import com.chathurangashan.mvvmerrorhandling.di.modules.AppModule
import com.chathurangashan.mvvmerrorhandling.di.modules.NetworkModule
import com.chathurangashan.mvvmerrorhandling.di.subcomponents.ActivitySubComponent
import com.chathurangashan.mvvmerrorhandling.di.subcomponents.FragmentSubComponent
import com.chathurangashan.mvvmerrorhandling.di.subcomponents.SubComponentModule
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [AssistedInjectModule::class, AppModule::class, NetworkModule::class, SubComponentModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }

    fun activityComponent() : ActivitySubComponent.Factory
    fun fragmentComponent() : FragmentSubComponent.Factory

}

@AssistedModule
@Module(includes = [AssistedInject_AssistedInjectModule::class])
interface AssistedInjectModule