package com.example.app3.repository.api.client

import com.example.app3.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientRetrofit {

    companion object {
        private lateinit var INSTANCE: Retrofit

        private fun getRetrofitInstance(): Retrofit{
            val http = OkHttpClient.Builder()
            if(!::INSTANCE.isInitialized){
                INSTANCE = Retrofit.Builder().baseUrl(Constants.API.BASE_URL_WEATHER).client(http.build()).addConverterFactory(
                    GsonConverterFactory.create()
                ).build()
            }
            return INSTANCE
        }

        fun <S> createService(className: Class<S>): S{
            return getRetrofitInstance().create(className)
        }

    }
}