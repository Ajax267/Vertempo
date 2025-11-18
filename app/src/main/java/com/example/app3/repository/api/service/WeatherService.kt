package com.example.app3.repository.api.service

import com.example.app3.repository.api.model.WeatherEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather")
    fun getWeather(@Query("q")city: String,
                   @Query("appid")apiKey: String,
                   @Query("units")units: String = "metric",
                   @Query("lang")lang: String = "pt_br"
    ): Call<WeatherEntity>
}