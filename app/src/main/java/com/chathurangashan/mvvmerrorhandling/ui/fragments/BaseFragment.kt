package com.chathurangashan.mvvmerrorhandling.ui.fragments

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.afollestad.materialdialogs.MaterialDialog
import com.chathurangashan.mvvmerrorhandling.data.enums.OperationErrorType
import com.chathurangashan.mvvmerrorhandling.utilities.OperationError
import com.chathurangashan.mvvmerrorhandling.viewmodel.BaseViewModel
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment(layoutResource: Int) : Fragment(layoutResource) {

    protected abstract val navigationController: NavController
    protected abstract val viewModel: BaseViewModel

    protected open fun initialization(onDataProcessing: (() ->Unit)?,
                                      onDataProcessingComplete: (() ->Unit)?) {
        observeOperationError()
        observeProcessingStatus(onDataProcessing,onDataProcessingComplete)
    }

    private fun observeProcessingStatus(
        onDataProcessing: (() -> Unit)?,
        onDataProcessingComplete: (() -> Unit)?
    ) {
        viewModel.isProcessing.observe(viewLifecycleOwner){
            if(it){
                onDataProcessing?.invoke()
            }else{
                onDataProcessingComplete?.invoke()
            }
        }
    }

    private fun observeOperationError() {

        viewModel.operationErrorLiveData.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.also { operationError ->
                handleError(operationError)
            }
        }
    }

    protected open fun handleError(operationError: OperationError) {

        when (operationError.errorType) {

            OperationErrorType.VALIDATION_ERROR -> {
                handleValidationError(operationError)
            }
            OperationErrorType.RESPONSE_ERROR -> {
                handleResponseError(operationError)
            }
            OperationErrorType.CONNECTION_ERROR -> {
                handleConnectionError(operationError)
            }
            OperationErrorType.PROCESSING_ERROR -> {
               handleProcessingError(operationError)
            }
        }
    }

    protected open fun handleValidationError(operationError: OperationError){}

    protected open fun handleResponseError(operationError: OperationError){
        showDialog(operationError.messageTitle,operationError.message)
    }

    protected open fun handleConnectionError(operationError: OperationError){
        showDialog(operationError.messageTitle,operationError.message)
    }

    protected open fun handleProcessingError(operationError: OperationError){
        showDialog(operationError.messageTitle,operationError.message)
    }

    fun resolveErrorResource(value: Any): String{
        return if(value is Int){
            getString(value)
        }else{
            value.toString()
        }
    }

    protected open fun showDialog(title: String?, message: String?, navigateUp: Boolean = true) {

        if (title != null && message != null) {

            MaterialDialog(requireContext()).show {
                title(text = title)
                message(text = message)
                positiveButton(text = "OK")
                positiveButton {
                    if(navigateUp){
                        navigationController.navigateUp()
                    }
                }
            }
        }
    }

    protected open fun showToast(message: String, toastDisplayTime: Int){
        Toast.makeText(context,message,toastDisplayTime).show()
    }

    protected open fun showSnackBar(message: String, snackBarDisplayTime: Int){
        Snackbar.make(requireView().rootView, message, snackBarDisplayTime).show()
    }

}