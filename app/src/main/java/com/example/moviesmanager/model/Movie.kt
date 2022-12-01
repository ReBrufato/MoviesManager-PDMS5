package com.example.moviesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    var nome: String,
    var anoLancamento: String,
    var estudio: String?,//pode ser null, pois pode ser produtora
    var produtora: String?,//pode ser null, pois pode ser estúdio
    var duracao: Int,
    var flag: Boolean,//pode ser null
    var nota: Double?,
    var genero: String,
): Parcelable
