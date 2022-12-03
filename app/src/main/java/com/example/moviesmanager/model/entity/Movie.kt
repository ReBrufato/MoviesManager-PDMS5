package com.example.moviesmanager.model.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Movie(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @NonNull
    var nome: String,
    @NonNull
    var anoLancamento: String,
    var estudio: String?,
    var produtora: String?,
    @NonNull
    var duracao: String,
    @NonNull
    var flag: Boolean,
    var nota: Double?,
    @NonNull
    var genero: String,
): Parcelable
