package com.chathurangashan.mvvmerrorhandling.di.navigation

import androidx.navigation.NavController

interface ActivityNavigator {

    fun getNavController(hostFragmentId: Int): NavController

}