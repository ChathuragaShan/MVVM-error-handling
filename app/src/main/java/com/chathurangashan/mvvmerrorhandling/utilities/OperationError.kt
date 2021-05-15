package com.chathurangashan.mvvmerrorhandling.utilities

import com.chathurangashan.mvvmerrorhandling.data.enums.OperationErrorType

class OperationError private constructor(
        val errorType: OperationErrorType,
        val errorId: Int? = 1,
        val messageTitle: String? = null,
        val message: String? = null,
        val fieldErrors: Map<String, Any>? = null) {

    data class Builder(
            var errorType: OperationErrorType,
            var errorId: Int? = 1,
            var messageTitle: String? = null,
            var message: String? = null,
            var fieldErrors: Map<String, Any>? = null) {

        fun errorId(errorId: Int) = apply { this.errorId = errorId }
        fun messageTitle(messageTitle: String) = apply { this.messageTitle = messageTitle }
        fun message(message: String) = apply { this.message = message }
        fun fieldError(fieldErrors: Map<String, Any>) = apply { this.fieldErrors = fieldErrors }

        fun build() = OperationError(
                errorType,
                errorId,
                messageTitle = messageTitle,
                message = message,
                fieldErrors = fieldErrors
        )
    }
}
