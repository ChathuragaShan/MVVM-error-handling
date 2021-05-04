package com.chathurangashan.mvvmerrorhandling.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.chathurangashan.mvvmerrorhandling.R
import com.chathurangashan.mvvmerrorhandling.databinding.FragmentRegisterBinding

class RegisterFragment: Fragment(R.layout.fragment_register) {

    private lateinit var viewBinding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding  = FragmentRegisterBinding.bind(view)

        super.onViewCreated(view, savedInstanceState)
    }
}