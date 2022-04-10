package com.chathurangashan.mvvmerrorhandling.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import com.chathurangashan.mvvmerrorhandling.R
import com.chathurangashan.mvvmerrorhandling.databinding.FragmentWelcomeBinding
import com.chathurangashan.mvvmerrorhandling.di.injector
import com.chathurangashan.mvvmerrorhandling.di.subcomponents.FragmentSubComponent
import javax.inject.Inject


class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    private lateinit var viewBinding: FragmentWelcomeBinding
    private lateinit var fragmentSubComponent: FragmentSubComponent

    @Inject
    lateinit var navigationController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding = FragmentWelcomeBinding.bind(view)

        initialization()
        onClickLogin()
        onClickRegister()
    }

    private fun initialization() {

        fragmentSubComponent = injector.fragmentComponent().create(requireView())
        fragmentSubComponent.inject(this)
    }

    private fun onClickLogin(){

        viewBinding.loginButton.setOnClickListener {
            navigationController.navigate(R.id.to_login)
        }
    }

    private fun onClickRegister(){

        viewBinding.registerButton.setOnClickListener {
            navigationController.navigate(R.id.to_register)
        }
    }
}