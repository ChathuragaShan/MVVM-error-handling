package com.chathurangashan.mvvmerrorhandling.di.subcomponents

import android.view.View
import com.chathurangashan.mvvmerrorhandling.di.scopes.FragmentScope
import com.chathurangashan.mvvmerrorhandling.di.modules.FragmentModule
import com.chathurangashan.mvvmerrorhandling.ui.fragments.LoginFragment
import com.chathurangashan.mvvmerrorhandling.ui.fragments.RegisterFragment
import com.chathurangashan.mvvmerrorhandling.ui.fragments.WelcomeFragment
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

    fun inject(fragment: WelcomeFragment)

    fun inject(fragment: LoginFragment)

    fun inject(fragment: RegisterFragment)
    val registerViewModel: RegisterViewModel

}