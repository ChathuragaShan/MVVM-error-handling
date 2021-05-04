package com.chathurangashan.mvvmerrorhandling.di.modules

import android.view.View
import androidx.navigation.Navigation
import com.chathurangashan.mvvmerrorhandling.di.scopes.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class FragmentModule {

    @FragmentScope
    @Provides
    fun navigation(view : View) =
            Navigation.findNavController(view)
}