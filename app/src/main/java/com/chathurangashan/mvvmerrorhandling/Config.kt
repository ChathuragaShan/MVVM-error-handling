package com.chathurangashan.mvvmerrorhandling

sealed class Config {

    companion object{

        const val LIVE_BASE_URL = "https://release-url.com"
        const val DEV_BASE_URL = "https://release-url.com"

    }

}