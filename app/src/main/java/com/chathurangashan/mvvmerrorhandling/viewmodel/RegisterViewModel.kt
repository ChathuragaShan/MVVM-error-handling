package com.chathurangashan.mvvmerrorhandling.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.chathurangashan.mvvmerrorhandling.R
import com.chathurangashan.mvvmerrorhandling.data.moshi.RegisterRequest
import com.chathurangashan.mvvmerrorhandling.repositories.RegisterRepository
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


class RegisterViewModel @Inject constructor(private val repository: RegisterRepository)
    :BaseViewModel(repository) {

    val registerStatusLiveData : LiveData<String>

    init {
        registerStatusLiveData = Transformations.map(repository.registerLiveData){
            isProcessing.value = false
            return@map it
        }
    }

    fun registerUser(requestBody: RegisterRequest){

        isProcessing.value = true
        val isFormValid = validateRegisterForm(requestBody)

        if(isFormValid.first){
            viewModelScope.launch{
                repository.registerUser(requestBody)
            }
        }else{
            validationError(isFormValid.second)
        }

    }

    /**
     * Function responsible for doing form validation before make the register network call
     *
     * @param requestBody: request body data which contains user entered form details.
     * @return
     */
    private fun validateRegisterForm(requestBody: RegisterRequest): Pair<Boolean, Map<String, Any>>{

        var isFormValid = true
        val fieldErrors = mutableMapOf<String, Any> ()

        if(requestBody.userName.isBlank()){
            fieldErrors[usernameErrorKey] = R.string.empty_user_name_error
            isFormValid = false
        }else if(requestBody.userName.matches(".*\\s.*".toRegex())){
            fieldErrors[usernameErrorKey] = R.string.user_name_with_space_error
            isFormValid = false
        }else{
            fieldErrors.remove(usernameErrorKey)
        }

        if(requestBody.email.isBlank()){
            fieldErrors[emailErrorKey] = R.string.empty_email_error_message
            isFormValid = false
        }else if(requestBody.email.matches(".*\\s.*".toRegex())){
            fieldErrors[emailErrorKey] = R.string.email_with_space_error
            isFormValid = false
        }else{
            fieldErrors.remove(emailErrorKey)
        }

        if(requestBody.password.isBlank()){
            fieldErrors[passwordErrorKey] = R.string.empty_password_error
            isFormValid = false
        }else if(requestBody.password.matches(".*\\s.*".toRegex())){
            fieldErrors[passwordErrorKey] = R.string.password_with_space_error
            isFormValid = false
        }else if(requestBody.password.length <= 8){
            fieldErrors[passwordErrorKey] = R.string.password_length_error
            isFormValid = false
        }else if(!requestBody.password.matches(".*\\d.*".toRegex())){
            fieldErrors[passwordErrorKey] = R.string.missing_number_in_password_error
            isFormValid = false
        }else if(requestBody.password == requestBody.password.toLowerCase(Locale.ROOT)){
            fieldErrors[passwordErrorKey] = R.string.missing_upper_case_letter_in_password_error
            isFormValid = false
        }else{
            fieldErrors.remove(passwordErrorKey)
        }

        if(requestBody.confirmPassword.isBlank()){
            fieldErrors[confirmPasswordKey] = R.string.empty_confirm_password_error
            isFormValid = false
        }else if(requestBody.confirmPassword.matches(".*\\s.*".toRegex())){
            fieldErrors[confirmPasswordKey] = R.string.confirm_password_with_space_error
            isFormValid = false
        }else if(requestBody.confirmPassword != requestBody.password){
            fieldErrors[confirmPasswordKey] = R.string.password_mismatch_error
            isFormValid = false
        }else{
            fieldErrors.remove(confirmPasswordKey)
        }

        return Pair(isFormValid, fieldErrors)
    }

    companion object {
        const val usernameErrorKey = "usernameFiled"
        const val emailErrorKey = "emailFiled"
        const val passwordErrorKey = "passwordField"
        const val confirmPasswordKey = "confirmPasswordField"
    }

}