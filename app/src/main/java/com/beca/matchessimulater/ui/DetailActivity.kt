package com.beca.matchessimulater.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.beca.matchessimulater.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Setar a ActionBar como personalizado
        setSupportActionBar(binding.toolbar)
        //Tratamento para ter os elementos de seta de "voltar" e os controles na barra
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}