package com.chathurangashan.mvvmerrorhandling.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import com.chathurangashan.mvvmerrorhandling.R
import com.chathurangashan.mvvmerrorhandling.data.moshi.RegisterRequest
import com.chathurangashan.mvvmerrorhandling.databinding.FragmentHomeBinding
import com.chathurangashan.mvvmerrorhandling.di.injector
import com.chathurangashan.mvvmerrorhandling.di.subcomponents.FragmentSubComponent
import com.chathurangashan.mvvmerrorhandling.di.viewModel
import com.chathurangashan.mvvmerrorhandling.utilities.OperationError
import com.chathurangashan.mvvmerrorhandling.viewmodel.RegisterViewModel
import javax.inject.Inject

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    @Inject
    override lateinit var navigationController: NavController
    override val viewModel by viewModel { fragmentSubComponent.registerViewModel }

    private lateinit var fragmentSubComponent: FragmentSubComponent
    private lateinit var viewBinding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding = FragmentHomeBinding.bind(view)

        initialization()
        observeRegisterStatus()
        onClickRegisterButton()
    }

    private fun initialization() {

        fragmentSubComponent = injector.fragmentComponent().create(requireView())
        fragmentSubComponent.inject(this)

        super.initialization({ onDataProcessing() }, { onDataProcessingComplete() })
    }

    private fun observeRegisterStatus(){
        viewModel.registerStatusLiveData.observe(viewLifecycleOwner){
            showToast(it,Toast.LENGTH_LONG)
        }
    }

    private fun onClickRegisterButton() {
        viewBinding.registerButton.setOnClickListener {

            val username = viewBinding.userNameEditText.text.toString()
            val email = viewBinding.emailEditText.text.toString()
            val password = viewBinding.passwordEditText.text.toString()
            val passwordConfirm = viewBinding.passwordConfirmEditText.text.toString()

            viewBinding.userNameInputLayout.error = null
            viewBinding.userNameInputLayout.isErrorEnabled = false
            viewBinding.emailInputLayout.error = null
            viewBinding.emailInputLayout.isErrorEnabled = false
            viewBinding.passwordInputLayout.error = null
            viewBinding.passwordInputLayout.isErrorEnabled = false
            viewBinding.passwordConfirmInputLayout.error = null
            viewBinding.passwordConfirmInputLayout.isErrorEnabled = false

            val registerRequest = RegisterRequest(username, email, password, passwordConfirm)
            viewModel.registerUser(registerRequest)

        }
    }

    private fun showFormErrors(fieldErrors: Map<String, Any>) {

        fieldErrors.forEach {
            when (it.key) {
                RegisterViewModel.usernameErrorKey -> {
                    viewBinding.userNameInputLayout.error = resolveErrorResource(it.value)
                    viewBinding.userNameInputLayout.isErrorEnabled = true
                }
                RegisterViewModel.emailErrorKey -> {
                    viewBinding.emailInputLayout.error = resolveErrorResource(it.value)
                    viewBinding.emailInputLayout.isErrorEnabled = true
                }
                RegisterViewModel.passwordErrorKey -> {
                    viewBinding.passwordInputLayout.error =
                        resolveErrorResource(it.value)
                    viewBinding.passwordInputLayout.isErrorEnabled = true
                }
                RegisterViewModel.confirmPasswordKey -> {
                    viewBinding.passwordConfirmInputLayout.error =
                        resolveErrorResource(it.value)
                    viewBinding.passwordConfirmInputLayout.isErrorEnabled = true
                }
            }
        }
    }

    override fun handleValidationError(operationError: OperationError) {
        if (!operationError.fieldErrors.isNullOrEmpty()) {
            showFormErrors(operationError.fieldErrors)
        }
    }

    override fun handleResponseError(operationError: OperationError) {
        
        if (!operationError.fieldErrors.isNullOrEmpty()) {
            showFormErrors(operationError.fieldErrors)
        }else{
            showDialog(operationError.messageTitle,operationError.message)
        }
    }

    private fun onDataProcessing(){
        viewBinding.loadingIndicator.visibility = View.VISIBLE
        viewBinding.registerButton.visibility = View.GONE
    }

    private fun onDataProcessingComplete() {
        viewBinding.loadingIndicator.visibility = View.GONE
        viewBinding.registerButton.visibility = View.VISIBLE
    }
}