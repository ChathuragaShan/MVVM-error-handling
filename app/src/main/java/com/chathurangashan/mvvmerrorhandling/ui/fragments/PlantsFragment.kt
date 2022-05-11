package com.chathurangashan.mvvmerrorhandling.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chathurangashan.mvvmerrorhandling.R
import com.chathurangashan.mvvmerrorhandling.databinding.FragmentPlantsBinding
import com.chathurangashan.mvvmerrorhandling.di.injector
import com.chathurangashan.mvvmerrorhandling.di.subcomponents.FragmentSubComponent
import com.chathurangashan.mvvmerrorhandling.di.viewModel
import com.chathurangashan.mvvmerrorhandling.ui.adapters.PlantsListAdapter
import com.chathurangashan.mvvmerrorhandling.utilities.DisplayUtils
import com.chathurangashan.mvvmerrorhandling.utilities.VerticalSpaceItemDecorator
import javax.inject.Inject


class PlantsFragment : BaseFragment(R.layout.fragment_plants) {

    @Inject
    override lateinit var navigationController: NavController

    override val viewModel by viewModel {fragmentSubComponent.plantsViewModel}

    private lateinit var fragmentSubComponent: FragmentSubComponent
    private lateinit var viewBinding: FragmentPlantsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding = FragmentPlantsBinding.bind(view)

        initialization()
        observePlantsStatus()
    }

    private fun initialization(){

        fragmentSubComponent = injector.fragmentComponent().create(requireView())
        fragmentSubComponent.inject(this)

        super.initialization({ onDataProcessing() },
            { onDataProcessingCompleteOrError() },
            { onDataProcessingCompleteOrError() })

    }

    private fun observePlantsStatus(){

        viewModel.plantsLiveData.observe(viewLifecycleOwner){ plantList ->

            val plantsListAdapter = PlantsListAdapter(plantList) { onSelectPlant(it) }
            viewBinding.plantsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            viewBinding.plantsRecyclerView.addItemDecoration(
                VerticalSpaceItemDecorator(DisplayUtils.pxFromDp(requireContext(), 8f).toInt())
            )
            viewBinding.plantsRecyclerView.adapter = plantsListAdapter

        }

    }

    private fun onSelectPlant(position: Int) {

    }

    private fun onDataProcessing(){
        viewBinding.loadingIndicator.visibility = View.VISIBLE
        viewBinding.plantsRecyclerView.visibility = View.GONE
    }

    private fun onDataProcessingCompleteOrError() {
        viewBinding.loadingIndicator.visibility = View.GONE
        viewBinding.plantsRecyclerView.visibility = View.VISIBLE
    }


}