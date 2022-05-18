package com.chathurangashan.mvvmerrorhandling.ui.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.chathurangashan.mvvmerrorhandling.R
import com.chathurangashan.mvvmerrorhandling.databinding.ActivityMainBinding
import com.chathurangashan.mvvmerrorhandling.di.navigation.ActivityNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: ActivityNavigator
    private lateinit var navigationController : NavController
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initialization()
    }

    private fun initialization() {

        //injector.activityComponent().create(this,R.id.hostFragment).inject(this)

        navigationController = navigator.getNavController(R.id.hostFragment)

        setSupportActionBar(viewBinding.toolbar)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.plantsFragment))

        NavigationUI.setupActionBarWithNavController(this, navigationController,appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {

        if(navigationController.currentDestination?.id == R.id.welcomeFragment
            || navigationController.currentDestination?.id == R.id.plantsFragment){
            moveTaskToBack(true)
        }else{
            navigationController.navigateUp()
        }

        return true
    }

    override fun onBackPressed() {
        onSupportNavigateUp()
    }
}