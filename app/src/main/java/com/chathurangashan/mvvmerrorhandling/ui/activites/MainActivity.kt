package com.chathurangashan.mvvmerrorhandling.ui.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.chathurangashan.mvvmerrorhandling.R
import com.chathurangashan.mvvmerrorhandling.databinding.ActivityMainBinding
import com.chathurangashan.mvvmerrorhandling.di.injector
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigationController : NavController
    lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initialization()
    }

    private fun initialization() {

        injector.activityComponent().create(this,R.id.hostFragment).inject(this)

        setSupportActionBar(viewBinding.toolbar)
        NavigationUI.setupActionBarWithNavController(this, navigationController)
    }

    override fun onSupportNavigateUp(): Boolean {

        if(navigationController.currentDestination?.id == R.id.homeFragment){
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