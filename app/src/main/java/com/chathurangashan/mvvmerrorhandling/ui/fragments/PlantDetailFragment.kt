package com.chathurangashan.mvvmerrorhandling.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import com.chathurangashan.mvvmerrorhandling.R
import com.chathurangashan.mvvmerrorhandling.databinding.FragmentPlantDetailBinding
import com.chathurangashan.mvvmerrorhandling.di.injector
import com.chathurangashan.mvvmerrorhandling.di.subcomponents.FragmentSubComponent
import com.chathurangashan.mvvmerrorhandling.viewmodel.PlantDetailsViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_plant_detail.*
import java.text.DecimalFormat
import javax.inject.Inject

class PlantDetailFragment : BaseFragment(R.layout.fragment_plant_detail) {

    override lateinit var viewModel: PlantDetailsViewModel
    private lateinit var fragmentSubComponent: FragmentSubComponent
    private lateinit var viewBinding: FragmentPlantDetailBinding
    @Inject
    override lateinit var navigationController: NavController

    private var plantId = 0

    private val decimalFormatFormat = DecimalFormat("#.00")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding = FragmentPlantDetailBinding.bind(view)

        initialization()
        observePlantDetails()

    }

    private fun initialization(){

        fragmentSubComponent = injector.fragmentComponent().create(requireView())
        fragmentSubComponent.inject(this)


        arguments?.also {

            plantId = PlantDetailFragmentArgs.fromBundle(it).plantId
            viewModel = fragmentSubComponent.plantDetailsViewModel.create(plantId)
        }


        super.initialization({ onDataProcessing() },
            { onDataProcessingComplete() },
            { onDataProcessingError() })
    }

    private fun observePlantDetails(){

        viewModel.plantsDetailLiveData.observe(viewLifecycleOwner){

            plantName.text = it.name
            plantPrice.text = "$ ${decimalFormatFormat.format(it.price)}"
            plantDescription.text = getString(it.description)
            Picasso.get().load(it.image).into(plantImageView)
            size1button.text = it.sizes[0]
            size1button.text = it.sizes[1]
            planter1Button.text = it.planters[0]
            planter2Button.text = it.planters[1]
            planter3Button.text = it.planters[2]
        }
    }

    private fun onDataProcessing(){
        viewBinding.loadingIndicator.visibility = View.VISIBLE
        viewBinding.scrollViewContainer.visibility = View.GONE
    }

    private fun onDataProcessingComplete() {
        viewBinding.loadingIndicator.visibility = View.GONE
        viewBinding.scrollViewContainer.visibility = View.VISIBLE
    }

    private fun onDataProcessingError(){
        viewBinding.loadingIndicator.visibility = View.GONE
        viewBinding.scrollViewContainer.visibility = View.GONE
    }
}