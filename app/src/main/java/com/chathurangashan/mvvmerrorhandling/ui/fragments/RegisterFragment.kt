package com.chathurangashan.mvvmerrorhandling.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.NavController
import com.chathurangashan.mvvmerrorhandling.R
import com.chathurangashan.mvvmerrorhandling.data.moshi.requests.RegisterRequest
import com.chathurangashan.mvvmerrorhandling.databinding.FragmentRegisterBinding
import com.chathurangashan.mvvmerrorhandling.di.injector
import com.chathurangashan.mvvmerrorhandling.di.subcomponents.FragmentSubComponent
import com.chathurangashan.mvvmerrorhandling.di.viewModel
import com.chathurangashan.mvvmerrorhandling.utilities.OperationError
import com.chathurangashan.mvvmerrorhandling.viewmodel.RegisterViewModel
import javax.inject.Inject

class RegisterFragment : BaseFragment(R.layout.fragment_register) {

    @Inject
    override lateinit var navigationController: NavController
    override val viewModel by viewModel { fragmentSubComponent.registerViewModel }

    private lateinit var fragmentSubComponent: FragmentSubComponent
    private lateinit var viewBinding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding = FragmentRegisterBinding.bind(view)

        initialization()
        observeRegisterStatus()
        onClickRegisterButton()
        usernameChangeListener()
        emailChangeListener()
        passwordChangeListener()
        passwordConfirmChangeListener()

    }

    private fun initialization() {

        fragmentSubComponent = injector.fragmentComponent().create(requireView())
        fragmentSubComponent.inject(this)

        super.initialization({ onDataProcessing() },
            { onDataProcessingCompleteOrError() },
            { onDataProcessingCompleteOrError() })
    }

    private fun observeRegisterStatus(){

        viewModel.registerStatusLiveData.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let { toastMessage ->
                showToast(toastMessage,Toast.LENGTH_LONG)
                navigationController.navigateUp()
            }
        }

    }

    private fun usernameChangeListener(){

        viewBinding.userNameEditText.doAfterTextChanged {
            viewModel.usernameValidation(it.toString())
        }

    }

    private fun emailChangeListener(){

        viewBinding.emailEditText.doAfterTextChanged {
            viewModel.emailValidation(it.toString())
        }
    }

    private fun passwordChangeListener(){

        viewBinding.passwordEditText.doAfterTextChanged {
            viewModel.passwordValidation(it.toString())
        }

    }

    private fun passwordConfirmChangeListener(){

        viewBinding.passwordConfirmEditText.doAfterTextChanged {
            viewModel.confirmPasswordValidation(
                it.toString(),
                viewBinding.passwordEditText.text.toString()
            )
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
            viewModel.validateRegisterForm(registerRequest)

            if (viewModel.fieldErrors.filter { it.value != null }.isEmpty()) {
                viewModel.registerUser(registerRequest)
            }

        }

    }

    private fun showFormErrors(fieldErrors: Map<String, Any?>) {

        fieldErrors.forEach {

            when (it.key) {
                RegisterViewModel.usernameErrorKey -> {
                    viewBinding.userNameInputLayout.isErrorEnabled = it.value != null
                    if(it.value != null) {
                        viewBinding.userNameInputLayout.error = resolveErrorResource(it.value!!)
                    }
                }
                RegisterViewModel.emailErrorKey -> {
                    viewBinding.emailInputLayout.isErrorEnabled = it.value != null
                    if(it.value != null) {
                        viewBinding.emailInputLayout.error = resolveErrorResource(it.value!!)
                    }
                }
                RegisterViewModel.passwordErrorKey -> {
                    viewBinding.passwordInputLayout.isErrorEnabled = it.value != null
                    if(it.value != null) {
                        viewBinding.passwordInputLayout.error = resolveErrorResource(it.value!!)
                    }
                }
                RegisterViewModel.confirmPasswordKey -> {
                    viewBinding.passwordConfirmInputLayout.isErrorEnabled = it.value != null
                    if(it.value != null){
                        viewBinding.passwordConfirmInputLayout.error = resolveErrorResource(it.value!!)
                    }
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
            showDialog(operationError.messageTitle,operationError.message,false)
        }
    }

    private fun onDataProcessing(){
        viewBinding.loadingIndicator.visibility = View.VISIBLE
        viewBinding.registerButton.visibility = View.GONE
    }

    private fun onDataProcessingCompleteOrError() {
        viewBinding.loadingIndicator.visibility = View.GONE
        viewBinding.registerButton.visibility = View.VISIBLE
    }
}