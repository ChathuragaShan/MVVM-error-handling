package com.chathurangashan.mvvmerrorhandling.di.navigation

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import javax.inject.Inject

class FragmentNavigatorImplementation @Inject constructor(): FragmentNavigator {

    override fun getNaveHostFragment(view: View): NavController {
        return Navigation.findNavController(view)
    }
}