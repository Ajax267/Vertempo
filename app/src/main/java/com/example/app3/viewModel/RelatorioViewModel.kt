import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app3.model.PrevisaoTempo
import com.example.app3.repository.api.client.ClientRetrofit
import com.example.app3.repository.api.model.WeatherEntity
import com.example.app3.repository.api.service.WeatherService
import com.example.app3.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RelatorioViewModel : ViewModel() {

    private val previsao = MutableLiveData<PrevisaoTempo>()
    private val errorMessage = MutableLiveData<String>()

    fun getPrevisao(): LiveData<PrevisaoTempo> = previsao
    fun getErrorMessage(): LiveData<String> = errorMessage

    fun requestWeather(city: String) {
        val apiWeatherService =
            ClientRetrofit.createService(WeatherService::class.java)

        val call = apiWeatherService.getWeather(
            city = city,
            apiKey = Constants.API.API_KEY_WEATHER,
            units = "metric",
            lang = "pt_br"
        )

        call.enqueue(object : Callback<WeatherEntity> {
            override fun onResponse(
                call: Call<WeatherEntity>,
                response: Response<WeatherEntity>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        previsao.value = body.toPrevisaoTempo()
                    } else {
                        errorMessage.value = "Resposta vazia da API"
                    }
                } else {
                    errorMessage.value = "Erro ${response.code()} na API"
                }
            }

            override fun onFailure(call: Call<WeatherEntity>, t: Throwable) {
                errorMessage.value = "Falha na requisição: ${t.message}"
            }
        })
    }
}

fun WeatherEntity.toPrevisaoTempo(): PrevisaoTempo {
    val main = this.main
    val info = this.weather?.firstOrNull()
    val wind = this.wind

    return PrevisaoTempo(
        city = this.name,
        description = info?.description ?: "",
        conditionMain = info?.main ?: "",
        temp = main?.temp?.toInt() ?: 0,
        tempMin = main?.tempMin?.toInt() ?: 0,
        tempMax = main?.tempMax?.toInt() ?: 0,
        feelsLike = main?.feelsLike?.toInt() ?: 0,
        humidity = main?.humidity ?: 0,
        windSpeed = wind?.speed?.toInt() ?: 0
    )
}
