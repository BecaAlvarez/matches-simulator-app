package com.beca.matchessimulater.domain

import com.google.gson.annotations.SerializedName

data class Match(
    //Traduzir o que está na API
    @SerializedName("descricao")
    val description: String,
    @SerializedName("local")
    val place: Place,
    @SerializedName("mandante")
    val homeTeam: Team,
    @SerializedName("visitante")
    val awayTeam: Team

)
