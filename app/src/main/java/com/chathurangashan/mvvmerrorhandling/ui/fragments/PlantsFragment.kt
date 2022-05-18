package com.chathurangashan.mvvmerrorhandling.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chathurangashan.mvvmerrorhandling.R
import com.chathurangashan.mvvmerrorhandling.databinding.FragmentPlantsBinding
import com.chathurangashan.mvvmerrorhandling.di.navigation.FragmentNavigator
import com.chathurangashan.mvvmerrorhandling.ui.adapters.PlantsListAdapter
import com.chathurangashan.mvvmerrorhandling.utilities.DisplayUtils
import com.chathurangashan.mvvmerrorhandling.utilities.VerticalSpaceItemDecorator
import com.chathurangashan.mvvmerrorhandling.viewmodel.PlantsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlantsFragment : BaseFragment(R.layout.fragment_plants) {

    @Inject
    lateinit var navigator: FragmentNavigator
    override lateinit var navigationController: NavController
    private lateinit var viewBinding: FragmentPlantsBinding
    override val viewModel: PlantsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding = FragmentPlantsBinding.bind(view)
        navigationController = navigator.getNaveHostFragment(view)

        initialization()
        observePlantsStatus()
    }

    private fun initialization(){

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

        val plantDetailsDirection = PlantsFragmentDirections.toPlantDetails(position)
        navigationController.navigate(plantDetailsDirection)

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