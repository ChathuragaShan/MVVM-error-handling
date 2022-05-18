package com.chathurangashan.mvvmerrorhandling.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.NavController
import com.chathurangashan.mvvmerrorhandling.R
import com.chathurangashan.mvvmerrorhandling.databinding.FragmentWelcomeBinding
import com.chathurangashan.mvvmerrorhandling.di.navigation.FragmentNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    @Inject
    lateinit var navigator: FragmentNavigator
    lateinit var navigationController: NavController
    private lateinit var viewBinding: FragmentWelcomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding = FragmentWelcomeBinding.bind(view)
        navigationController = navigator.getNaveHostFragment(view)

        onClickLogin()
        onClickRegister()
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