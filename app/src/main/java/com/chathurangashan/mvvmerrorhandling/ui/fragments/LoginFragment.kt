package com.chathurangashan.mvvmerrorhandling.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.chathurangashan.mvvmerrorhandling.R
import com.chathurangashan.mvvmerrorhandling.data.moshi.requests.LoginRequest
import com.chathurangashan.mvvmerrorhandling.databinding.FragmentLoginBinding
import com.chathurangashan.mvvmerrorhandling.di.navigation.FragmentNavigator
import com.chathurangashan.mvvmerrorhandling.utilities.OperationError
import com.chathurangashan.mvvmerrorhandling.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {

    @Inject
    lateinit var navigator: FragmentNavigator
    override lateinit var navigationController: NavController
    private lateinit var viewBinding: FragmentLoginBinding
    override val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding = FragmentLoginBinding.bind(view)
        navigationController = navigator.getNaveHostFragment(view)

        initialization()
        observeLoginStatus()
        emailChangeListener()
        passwordChangeListener()
        onClickLoginButton()

    }

    private fun initialization() {

        super.initialization({ onDataProcessing() },
            { onDataProcessingCompleteOrError() },
            { onDataProcessingCompleteOrError() })
    }

    private fun observeLoginStatus() {

        viewModel.loginStatusLiveData.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let { toastMessage ->
                showToast(toastMessage.message, Toast.LENGTH_LONG)
                navigationController.navigate(R.id.to_plants)
            }
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

    private fun onClickLoginButton() {

        viewBinding.registerButton.setOnClickListener {


            val email = viewBinding.emailEditText.text.toString()
            val password = viewBinding.passwordEditText.text.toString()

            viewBinding.emailInputLayout.error = null
            viewBinding.emailInputLayout.isErrorEnabled = false
            viewBinding.passwordInputLayout.error = null
            viewBinding.passwordInputLayout.isErrorEnabled = false

            val loginRequest = LoginRequest(email, password)
            viewModel.validateLoginForm(loginRequest)

            if (viewModel.fieldErrors.filter { it.value != null }.isEmpty()) {
                viewModel.loginUser(loginRequest)
            }

        }

    }

    private fun showFormErrors(fieldErrors: Map<String, Any?>) {

        fieldErrors.forEach {

            when (it.key) {
                LoginViewModel.emailErrorKey -> {
                    viewBinding.emailInputLayout.isErrorEnabled = it.value != null
                    if(it.value != null) {
                        viewBinding.emailInputLayout.error = resolveErrorResource(it.value!!)
                    }
                }
                LoginViewModel.passwordErrorKey -> {
                    viewBinding.passwordInputLayout.isErrorEnabled = it.value != null
                    if(it.value != null) {
                        viewBinding.passwordInputLayout.error = resolveErrorResource(it.value!!)
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