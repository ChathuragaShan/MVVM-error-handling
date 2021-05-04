package com.chathurangashan.mvvmerrorhandling.ui.fragments

import androidx.fragment.app.Fragment
import androidx.navigation.NavController

abstract class BaseFragment(layoutResource: Int) : Fragment(layoutResource) {

    protected abstract val navigationController: NavController

}