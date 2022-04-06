package com.chathurangashan.mvvmerrorhandling.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor(val context: Context): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request()

        if(!isDeviceOnline(context)){
            throw NoConnectivityException()
        }

        return chain.proceed(request)
    }

    private fun isDeviceOnline(context: Context) : Boolean{

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities = connectivityManager.activeNetwork ?: return false

        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) -> true
            else -> false
        }
    }

    inner class NoConnectivityException: IOException()
}