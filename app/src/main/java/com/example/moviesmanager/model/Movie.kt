package com.example.moviesmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    var nome: String,
    var anoLancamento: String,
    var estudio: String?,
    var produtora: String?,
    var duracao: Int,
    var flag: Boolean,
    var nota: Double?,
    var genero: String,
): Parcelable
