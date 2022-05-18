package com.chathurangashan.mvvmerrorhandling.di.navigation

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import javax.inject.Inject

class ActivityNavigatorImplementation @Inject constructor(private val activity: FragmentActivity):
    ActivityNavigator {

    override fun getNavController(hostFragmentId: Int): NavController {
        val navHostFragment = activity.supportFragmentManager.findFragmentById(hostFragmentId) as NavHostFragment
        return navHostFragment.navController
    }

}