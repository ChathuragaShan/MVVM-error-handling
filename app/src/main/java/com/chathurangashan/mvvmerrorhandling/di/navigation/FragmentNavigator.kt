package com.chathurangashan.mvvmerrorhandling.di.navigation

import android.view.View
import androidx.navigation.NavController

interface FragmentNavigator {

    fun getNaveHostFragment(view: View): NavController
}