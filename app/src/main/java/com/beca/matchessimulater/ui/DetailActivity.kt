package com.beca.matchessimulater.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.beca.matchessimulater.databinding.ActivityDetailBinding
import com.beca.matchessimulater.domain.Match
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    //encapsular uma constante
    object Extras{
        const val MATCH = "EXTRA_MATCH"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Setar a ActionBar como personalizado
        setSupportActionBar(binding.toolbar)
        //Tratamento para ter os elementos de seta de "voltar" e os controles na barra
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadMatchFromExtra()

    }

    private fun loadMatchFromExtra() {
        intent?.extras?.getParcelable<Match>(Extras.MATCH)?.let{
            //contexto é this,load pega a imagem da partida. E ser redenrizada com o target binding.ivPlace
            Glide.with(this).load(it.place.image).into(binding.ivPlace)
            // Para aparece o nome do estadio no titulo da toolbar
            supportActionBar?.title = it.place.name
            //Desscrição da partida
            binding.tvDescription.text = it.description

            Glide.with(this).load(it.homeTeam.image).into(binding.ivHomeTeam)
            binding.tvHomeTeamName.text = it.homeTeam.name
            //Rating(estrelas do time)
            binding.rbHomeTeamStars.rating = it.homeTeam.stars.toFloat()
            if(it.homeTeam.score != null){
                binding.tvHomeTeamScore.text = it.homeTeam.stars.toString()
            }

            Glide.with(this).load(it.awayTeam.image).into(binding.ivAwayTeam)
            binding.tvAwayTeamName.text = it.awayTeam.name
            binding.rbAwayTeamStars.rating = it.awayTeam.stars.toFloat()
            if(it.homeTeam.score != null){
                binding.tvAwayTeamScore.text = it.awayTeam.stars.toString()
            }

        }
    }
}