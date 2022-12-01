package com.example.moviesmanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.moviesmanager.R
import com.example.moviesmanager.model.Movie

class MovieAdapter(context: Context, private val moviesList: MutableList<Movie>): ArrayAdapter<Movie>(context, R.layout.tile_movie, moviesList){

    private data class TileMovieHolder(val nomeTileMv: TextView, val duracaoTileMv: TextView, val notaTileMv: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val movie: Movie = moviesList[position]

        var movieTileView = convertView

        if(movieTileView == null) {
            movieTileView =
                (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.tile_movie,parent, false)

            val tileMovieHolder = TileMovieHolder(
                movieTileView.findViewById(R.id.nomeTileMv),
                movieTileView.findViewById(R.id.duracaoTileMv),
                movieTileView.findViewById(R.id.notaTileMv),)

            movieTileView.tag = tileMovieHolder
        }

        with(movieTileView?.tag as TileMovieHolder){
            nomeTileMv.text = "Filme: " + movie.nome
            duracaoTileMv.text = "Duração: " + movie.duracao.toString() + " min"

            //verifica se o usuário deu nota para o filme
            if (movie.nota == null){
                notaTileMv.text = "Nota: Não avaliado"
            }else{
                notaTileMv.text = "Nota: " + movie.nota.toString()
            }
        }

        return movieTileView
    }
}