package com.chathurangashan.mvvmerrorhandling.di.modules

import androidx.navigation.Navigation
import com.chathurangashan.mvvmerrorhandling.ui.activites.MainActivity
import com.chathurangashan.mvvmerrorhandling.di.scopes.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class ActivityModule {

    @ActivityScope
    @Provides
    fun navigation(activity: MainActivity, hosFragment: Int) =
        Navigation.findNavController(activity,hosFragment)
}