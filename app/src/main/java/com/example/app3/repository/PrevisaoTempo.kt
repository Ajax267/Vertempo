package com.example.app3.model

import java.io.Serializable

data class PrevisaoTempo(
    val city: String,
    val description: String,
    val conditionMain: String,
    val temp: Int,
    val tempMin: Int,
    val tempMax: Int,
    val feelsLike: Int,
    val humidity: Int,
    val windSpeed: Int
) : Serializable
