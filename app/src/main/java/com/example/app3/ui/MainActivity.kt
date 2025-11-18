package com.example.app3.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app3.R
import com.example.app3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gerarRelatorio.setOnClickListener(this)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.gerarRelatorio) {
            val city = binding.campoCidade.text.toString().trim()
            if (city.isEmpty()) {
                Toast.makeText(this, "Digite o nome da cidade", Toast.LENGTH_SHORT).show()
            } else {
                val bundle = bundleOf("CITY_NAME" to city)
                val intent = Intent(this, RelatorioActivity::class.java).apply {
                    putExtras(bundle)
                }
                startActivity(intent)
            }
        }
    }
}