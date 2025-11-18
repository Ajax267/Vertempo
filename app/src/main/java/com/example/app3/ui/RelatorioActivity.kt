package com.example.app3.ui

import RelatorioViewModel
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.app3.R
import com.example.app3.databinding.ActivityRelatorioBinding
import com.example.app3.model.PrevisaoTempo

class RelatorioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRelatorioBinding
    private lateinit var viewModel: RelatorioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRelatorioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVoltar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val city = intent.getStringExtra("CITY_NAME") ?: run {
            finish()
            return
        }

        viewModel = ViewModelProvider(this).get(RelatorioViewModel::class.java)

        viewModel.getPrevisao().observe(this) { previsao ->
            if (previsao != null) {
                preencherDados(previsao)
            }
        }

        viewModel.getErrorMessage().observe(this) { msg ->
            if (!msg.isNullOrEmpty()) {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        viewModel.requestWeather(city)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun preencherDados(previsaoTempo: PrevisaoTempo) {
        binding.tvCidade.text = previsaoTempo.city
        binding.tvResumo.text = "${previsaoTempo.temp}ºC, ${previsaoTempo.description}"
        binding.tvTempMax.text = "Temperatura máx: ${previsaoTempo.tempMax}ºC"
        binding.tvTempMin.text = "Temperatura mín: ${previsaoTempo.tempMin}ºC"
        binding.tvSensacao.text = "Sensação térmica: ${previsaoTempo.feelsLike}ºC"
        binding.tvUmidade.text = "Umidade do ar: ${previsaoTempo.humidity}%"
        binding.tvVento.text = "Velocidade do vento: ${previsaoTempo.windSpeed} km/h"

        atualizarIconeEstado(previsaoTempo)

    }

    private fun atualizarIconeEstado(previsao: PrevisaoTempo) {
        binding.imgSol.visibility = View.GONE
        binding.imgNublado.visibility = View.GONE
        binding.imgChuva.visibility = View.GONE

        val cond = previsao.conditionMain.lowercase()
        val desc = previsao.description.lowercase()

        when {
            cond.contains("clear") || desc.contains("limpo") -> {
                binding.imgSol.visibility = View.VISIBLE
            }

            cond.contains("cloud") || desc.contains("nublado") -> {
                binding.imgNublado.visibility = View.VISIBLE
            }

            cond.contains("rain") || desc.contains("chuva") -> {
                binding.imgChuva.visibility = View.VISIBLE
            }

            else -> {
                binding.imgNublado.visibility = View.VISIBLE
            }
        }
    }

}