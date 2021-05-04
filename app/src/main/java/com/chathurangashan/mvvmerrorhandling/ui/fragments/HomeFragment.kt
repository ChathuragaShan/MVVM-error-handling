package com.chathurangashan.mvvmerrorhandling.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import com.chathurangashan.mvvmerrorhandling.R
import com.chathurangashan.mvvmerrorhandling.databinding.FragmentHomeBinding
import com.chathurangashan.mvvmerrorhandling.di.injector
import com.chathurangashan.mvvmerrorhandling.di.subcomponents.FragmentSubComponent
import javax.inject.Inject

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    @Inject
    override lateinit var navigationController: NavController

    private lateinit var fragmentSubComponent: FragmentSubComponent
    private lateinit var viewBinding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding = FragmentHomeBinding.bind(view)

        initialization()
        onClickDatabaseExampleButton()
        onClickNetworkExampleButton()
    }

    private fun initialization() {

        fragmentSubComponent = injector.fragmentComponent().create(requireView())
        fragmentSubComponent.inject(this)
    }

    private fun onClickNetworkExampleButton() {
        viewBinding.networkExample.setOnClickListener {
            navigationController.navigate(R.id.to_profile)
        }
    }

    private fun onClickDatabaseExampleButton() {
        viewBinding.dataBaseExample.setOnClickListener {
            navigationController.navigate(R.id.to_register)
        }
    }
}