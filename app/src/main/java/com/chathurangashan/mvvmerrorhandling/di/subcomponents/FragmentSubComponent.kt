package com.chathurangashan.mvvmerrorhandling.di.subcomponents

import android.view.View
import com.chathurangashan.mvvmerrorhandling.di.scopes.FragmentScope
import com.chathurangashan.mvvmerrorhandling.di.modules.FragmentModule
import com.chathurangashan.mvvmerrorhandling.ui.fragments.HomeFragment
import com.chathurangashan.mvvmerrorhandling.viewmodel.RegisterViewModel
import dagger.BindsInstance
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [FragmentModule::class])
interface FragmentSubComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance view: View): FragmentSubComponent
    }

    fun inject(fragment: HomeFragment)
    val registerViewModel: RegisterViewModel

}