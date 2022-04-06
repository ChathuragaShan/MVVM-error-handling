package com.chathurangashan.mvvmerrorhandling.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.chathurangashan.mvvmerrorhandling.R
import com.chathurangashan.mvvmerrorhandling.data.moshi.RegisterRequest
import com.chathurangashan.mvvmerrorhandling.data.enums.ProcessingStatus
import com.chathurangashan.mvvmerrorhandling.repositories.RegisterRepository
import com.chathurangashan.mvvmerrorhandling.utilities.SingleLiveEvent
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


class RegisterViewModel @Inject constructor(private val repository: RegisterRepository)
    :BaseViewModel(repository) {

    val registerStatusLiveData : LiveData<SingleLiveEvent<String>>

    init {
        registerStatusLiveData = Transformations.map(repository.registerLiveData){
            isProcessing.value = ProcessingStatus.COMPLETED
            return@map it
        }
    }

    fun registerUser(requestBody: RegisterRequest){

        viewModelScope.launch {
            isProcessing.value = ProcessingStatus.PROCESSING
            repository.registerUser(requestBody)
        }

    }

    fun usernameValidation(userName: String){

        if(userName.isBlank()){
            fieldErrors[usernameErrorKey] = R.string.empty_user_name_error
        }else if(userName.matches(".*\\s.*".toRegex())){
            fieldErrors[usernameErrorKey] = R.string.user_name_with_space_error
        }else{
            fieldErrors[usernameErrorKey] = null
        }

        validationError(fieldErrors)
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

    fun confirmPasswordValidation(password: String ,confirmPassword: String){

        if(confirmPassword.isBlank()){
            fieldErrors[confirmPasswordKey] = R.string.empty_confirm_password_error
        }else if(confirmPassword.matches(".*\\s.*".toRegex())){
            fieldErrors[confirmPasswordKey] = R.string.confirm_password_with_space_error
        }else if(confirmPassword != password){
            fieldErrors[confirmPasswordKey] = R.string.password_mismatch_error
        }else{
            fieldErrors[confirmPasswordKey] = null
        }

        validationError(fieldErrors)

    }

    /**
     * Function responsible for doing form validation before make the register network call
     *
     * @param requestBody: request body data which contains user entered form details.
     * @return
     */
    fun validateRegisterForm(requestBody: RegisterRequest) {

        usernameValidation(requestBody.userName)
        emailValidation(requestBody.email)
        passwordValidation(requestBody.password)
        confirmPasswordValidation(requestBody.password, requestBody.confirmPassword)

    }

    companion object {
        const val usernameErrorKey = "usernameFiled"
        const val emailErrorKey = "emailFiled"
        const val passwordErrorKey = "passwordField"
        const val confirmPasswordKey = "confirmPasswordField"
    }

}