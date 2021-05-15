package com.chathurangashan.mvvmerrorhandling.network

import com.chathurangashan.mvvmerrorhandling.data.moshi.RegisterRequest
import com.chathurangashan.mvvmerrorhandling.data.moshi.register_response.Error
import com.chathurangashan.mvvmerrorhandling.data.moshi.register_response.RegisterResponse
import com.squareup.moshi.Moshi
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import java.io.IOException

/**
 * Network interceptor class that act as a remote server which supply the response accordingly
 */
class MockInterceptor : Interceptor {

    // Mock list of all user names represent the all the users in remote server data base
    private val allUserNames = listOf(
            "clayton_ferguson",
            "max_mccormick",
            "herbert_roberts",
            "carolyn_rodriquez",
            "chathuranga_shan")

    // Mock list of all user's email address represent the all the users in remote server data base
    private val allRegisteredEmails = listOf(
            "clayton.ferguson@gmail.com",
            "max.mccormick@gmail.com",
            "herbert.roberts@gmail.com",
            "carolyn.rodriquez@gmail.com",
            "chathuranga.shan@gmail.com"
    )

    private var attempts = 0
    private fun wantRandomError() = attempts++ % 5 == 0

    override fun intercept(chain: Interceptor.Chain): Response {

        Thread.sleep(1000) // fake delay

        val request = chain.request()
        val url = request.url.toString()

        if (url.endsWith("users/register")) {

            val requestBody = getRequestBody<RegisterRequest>(request)

            return if (!wantRandomError()) {
                processResponse(request, requestBody)
            } else {
                randomServerError(chain.request())
            }
        }

        return chain.proceed(request)
    }

    /**
     * responsible for getting the request body as a moshi object
     *
     * @param request: Retrofit request
     */
    private inline fun <reified T> getRequestBody(request: Request): T {

        val copy: Request = request.newBuilder().build()
        val buffer = Buffer()
        copy.body!!.writeTo(buffer)
        val requestBodyString = buffer.readUtf8()

        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(T::class.java)
        return jsonAdapter.fromJson(requestBodyString) as T
    }

    /**
     * Responsible for validating request data and returning response accordingly.
     */
    private fun processResponse(request: Request, registerRequest: RegisterRequest): Response {

        val username = registerRequest.userName
        val email = registerRequest.email

        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(RegisterResponse::class.java)

        if (!allUserNames.contains(username) && !allRegisteredEmails.contains(email)) {

            val registerErrorResponse =
                    RegisterResponse(true, "User registration completed.", null)

            return Response.Builder()
                    .code(200)
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .message("Input Field Error")
                    .body(jsonAdapter.toJson(registerErrorResponse)
                            .toResponseBody("application/json".toMediaType()))
                    .build()

        } else {

            val filedErrors = mutableListOf<Error>()
            if (allUserNames.contains(username)) {
                val userNameError = Error("user_name","Username already exists", )
                filedErrors.add(userNameError)
            }

            if (allRegisteredEmails.contains(email)) {
                val emailError = Error("email","Email already exists")
                filedErrors.add(emailError)
            }

            val registerErrorResponse =
                    RegisterResponse(false, "invalid filed values", filedErrors)

            return Response.Builder()
                    .code(200)
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .message("Input Field Error")
                    .body(jsonAdapter.toJson(registerErrorResponse)
                            .toResponseBody("application/json".toMediaType()))
                    .build()

        }

    }

    private fun randomServerError(request: Request): Response {

        return Response.Builder()
                .code(500)
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .message("Internal server error")
                .body("{}".toResponseBody("application/json".toMediaType()))
                .build()
    }

}