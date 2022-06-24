package com.beca.matchessimulater.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Place(

    @SerializedName("nome")
    val name: String,
    @SerializedName("imagem")
    val image: String,
): Parcelable
