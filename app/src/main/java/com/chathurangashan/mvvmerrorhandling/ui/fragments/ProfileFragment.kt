package com.chathurangashan.mvvmerrorhandling.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chathurangashan.mvvmerrorhandling.R
import com.chathurangashan.mvvmerrorhandling.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var viewBinding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding = FragmentProfileBinding.bind(view)
    }
}