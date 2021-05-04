package com.chathurangashan.mvvmerrorhandling.di.subcomponents

import com.chathurangashan.mvvmerrorhandling.di.scopes.ActivityScope
import com.chathurangashan.mvvmerrorhandling.ui.activites.MainActivity
import com.chathurangashan.mvvmerrorhandling.di.modules.ActivityModule
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivitySubComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance activity: MainActivity,
                   @BindsInstance hostFragment: Int): ActivitySubComponent
    }

    fun inject(MainActivity: MainActivity)
}