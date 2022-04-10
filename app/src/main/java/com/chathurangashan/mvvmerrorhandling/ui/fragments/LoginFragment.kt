package com.chathurangashan.mvvmerrorhandling.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.NavController
import com.chathurangashan.mvvmerrorhandling.R
import com.chathurangashan.mvvmerrorhandling.databinding.FragmentLoginBinding
import com.chathurangashan.mvvmerrorhandling.di.injector
import com.chathurangashan.mvvmerrorhandling.di.subcomponents.FragmentSubComponent
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var viewBinding: FragmentLoginBinding
    private lateinit var fragmentSubComponent: FragmentSubComponent

    @Inject
    lateinit var navigationController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding = FragmentLoginBinding.bind(view)

        initialization()
    }

    private fun initialization() {

        fragmentSubComponent = injector.fragmentComponent().create(requireView())
        fragmentSubComponent.inject(this)
    }
}