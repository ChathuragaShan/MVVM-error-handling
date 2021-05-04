package com.chathurangashan.mvvmerrorhandling.di

import android.app.Activity
import androidx.fragment.app.Fragment

interface InjectorProvider {
  val component: ApplicationComponent
}

val Activity.injector get() = (application as InjectorProvider).component
val Fragment.injector get() = (requireActivity().application as InjectorProvider).component
