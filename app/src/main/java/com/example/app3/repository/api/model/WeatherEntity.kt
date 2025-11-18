package com.example.app3.repository.api.model

import com.google.gson.annotations.SerializedName

class WeatherEntity {
    @SerializedName("name")
    var name: String = ""

    @SerializedName("main")
    var main: MainEntity? = null

    @SerializedName("weather")
    var weather: List<WeatherInfoEntity>? = null

    @SerializedName("wind")
    var wind: WindEntity? = null
}

class WindEntity {
    @SerializedName("speed")
    var speed: Double = 0.0
}

class WeatherInfoEntity {
    @SerializedName("main")
    var main: String = ""

    @SerializedName("description")
    var description: String = ""
}

class MainEntity{
    @SerializedName("temp")
    var temp: Double = 0.0
    @SerializedName("feels_like")
    var feelsLike: Double = 0.0
    @SerializedName("temp_min")
    var tempMin: Double = 0.0
    @SerializedName("temp_max")
    var tempMax: Double = 0.0
    @SerializedName("humidity")
    var humidity: Int = 0

}