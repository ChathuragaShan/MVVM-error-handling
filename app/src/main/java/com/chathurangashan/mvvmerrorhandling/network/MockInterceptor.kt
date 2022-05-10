package com.chathurangashan.mvvmerrorhandling.network

import android.content.SharedPreferences
import com.chathurangashan.mvvmerrorhandling.data.moshi.InputError
import com.chathurangashan.mvvmerrorhandling.data.moshi.requests.RegisterRequest
import com.chathurangashan.mvvmerrorhandling.data.moshi.requests.LoginRequest
import com.chathurangashan.mvvmerrorhandling.data.moshi.response.login.Data
import com.chathurangashan.mvvmerrorhandling.data.moshi.response.login.LoginResponse
import com.chathurangashan.mvvmerrorhandling.data.moshi.response.register.RegisterResponse
import com.squareup.moshi.Moshi
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer

/**
 * Network interceptor class that act as a remote server which supply the response accordingly
 */
class MockInterceptor (val sharedPreferences: SharedPreferences) : Interceptor {

    // Mock mutable map of all emails, username and password represent the all the data in remote server data base
    private val dummyDataBase = mutableMapOf(
        "clayton.ferguson@gmail.com" to Pair("clayton_ferguson","Clayton@1234"),
        "max.mccormick@gmail.com" to Pair("max_mccormick","Max@1234"),
        "herbert.roberts@gmail.com" to Pair("herbert_roberts","Herbert@123"),
        "carolyn.rodriquez@gmail.com" to Pair("carolyn_rodriquez","Carolyn@12345"),
        "chathuranga.shan@gmail.com" to  Pair("chathuranga_shan","Chathuranga@123")
    )

    private var attempts = 0
    private fun wantRandomError() = attempts++ % 5 == 0

    override fun intercept(chain: Interceptor.Chain): Response {

        Thread.sleep(1000) // fake delay

        val request = chain.request()
        val url = request.url.toString()

        if (url.endsWith("users/register")) {

            val loginRequestBody = getRequestBody<RegisterRequest>(request)
            return processRegisterResponse(request,loginRequestBody)

        }else if(url.endsWith("users/login")){

            val loginRequestBody = getRequestBody<LoginRequest>(request)
            return processLoginResponse(request,loginRequestBody)
        }

        return chain.proceed(request)
    }

    /**
     * Responsible for validating request data related to login request and returning response accordingly.
     * Server login session also handled here by using
     */
    private fun processLoginResponse(request: Request, loginRequest: LoginRequest): Response {

        val email = loginRequest.email
        val password = loginRequest.password

        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(LoginResponse::class.java)

        if (dummyDataBase.contains(email) && dummyDataBase[email]!!.second == password){

            val loginSuccessResponse = LoginResponse(
                true,
                "User login successful.",
                Data(dummyDataBase[email]!!.first),
                null
            )

            return Response.Builder()
                .code(200)
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .message("Success")
                .body(jsonAdapter.toJson(loginSuccessResponse)
                    .toResponseBody("application/json".toMediaType()))
                .build()

        }else{

            val filedErrors = mutableListOf<InputError>()

            if (!dummyDataBase.containsKey(email)) {
                val emailError = InputError("email","Email does not exist in the system")
                filedErrors.add(emailError)
            }

            val loginErrorResponse =
                LoginResponse(false, "invalid input data",null, filedErrors)

            return Response.Builder()
                .code(200)
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .message("Input Field Error")
                .body(jsonAdapter.toJson(loginErrorResponse)
                    .toResponseBody("application/json".toMediaType()))
                .build()

        }

    }

    /**
     * Responsible for validating request data related to Register request and returning response accordingly.
     */
    private fun processRegisterResponse(request: Request, registerRequest: RegisterRequest): Response {

        val username = registerRequest.userName
        val email = registerRequest.email
        val password = registerRequest.password

        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(RegisterResponse::class.java)

        if (!dummyDataBase.containsKey(email) && !isUserNameExist(username)) {

            val registerSuccessResponse =
                    RegisterResponse(true, "User registration completed.", null)

            dummyDataBase[email] = Pair(username, password)

            return Response.Builder()
                    .code(200)
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .message("Success")
                    .body(jsonAdapter.toJson(registerSuccessResponse)
                            .toResponseBody("application/json".toMediaType()))
                    .build()

        } else {

            val filedErrors = mutableListOf<InputError>()

            if (isUserNameExist(username)) {
                val userNameError = InputError("user_name","Username already exists")
                filedErrors.add(userNameError)
            }

            if (dummyDataBase.containsKey(email)) {
                val userNameError = InputError("email","Email already exists")
                filedErrors.add(userNameError)
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

    private fun isUserNameExist(username: String): Boolean{

        dummyDataBase.values.forEach {
            if (it.first == username){
                return true
            }
        }

        return false
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