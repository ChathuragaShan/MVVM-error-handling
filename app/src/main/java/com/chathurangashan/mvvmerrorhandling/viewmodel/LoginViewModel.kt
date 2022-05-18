package com.chathurangashan.mvvmerrorhandling.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.chathurangashan.mvvmerrorhandling.R
import com.chathurangashan.mvvmerrorhandling.data.enums.ProcessingStatus
import com.chathurangashan.mvvmerrorhandling.data.general.LoginDetails
import com.chathurangashan.mvvmerrorhandling.data.moshi.requests.LoginRequest
import com.chathurangashan.mvvmerrorhandling.repositories.LoginRepository
import com.chathurangashan.mvvmerrorhandling.utilities.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val repository: LoginRepository): BaseViewModel(repository){

    val loginStatusLiveData : LiveData<SingleLiveEvent<LoginDetails>>

    init {

        loginStatusLiveData = Transformations.map(repository.loginLiveData){
            isProcessing.value = ProcessingStatus.COMPLETED
            return@map it
        }

    }

    fun loginUser(loginRequest: LoginRequest){

        viewModelScope.launch {

            isProcessing.value = ProcessingStatus.PROCESSING
            repository.login(loginRequest)

        }

    }

    fun emailValidation(email: String){

        if(email.isBlank()){
            fieldErrors[emailErrorKey] = R.string.empty_email_error_message
        }else if(email.matches(".*\\s.*".toRegex())){
            fieldErrors[emailErrorKey] = R.string.email_with_space_error
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            fieldErrors[emailErrorKey] = R.string.invalid_email_error
        }else{
            fieldErrors[emailErrorKey] = null
        }

        validationError(fieldErrors)

    }

    fun passwordValidation(password: String){

        if(password.isBlank()){
            fieldErrors[passwordErrorKey] = R.string.empty_password_error
        }else if(password.matches(".*\\s.*".toRegex())){
            fieldErrors[passwordErrorKey] = R.string.password_with_space_error
        }else if(password.length <= 8){
            fieldErrors[passwordErrorKey] = R.string.password_length_error
        }else if(!password.matches(".*\\d.*".toRegex())){
            fieldErrors[passwordErrorKey] = R.string.missing_number_in_password_error
        }else if(password == password.toLowerCase(Locale.ROOT)){
            fieldErrors[passwordErrorKey] = R.string.missing_upper_case_letter_in_password_error
        }else{
            fieldErrors[passwordErrorKey] = null
        }

        validationError(fieldErrors)
    }

    /**
     * Function responsible for doing form validation before make the login network call
     *
     * @param requestBody: request body data which contains user entered form details.
     */
    fun validateLoginForm(requestBody: LoginRequest){

        emailValidation(requestBody.email)
        passwordValidation(requestBody.password)

    }

    companion object {
        const val emailErrorKey = "emailFiled"
        const val passwordErrorKey = "passwordField"
    }
}