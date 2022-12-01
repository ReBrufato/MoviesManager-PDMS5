package com.example.moviesmanager.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviesmanager.R
import com.example.moviesmanager.adapters.MovieAdapter
import com.example.moviesmanager.databinding.ActivityMainBinding
import com.example.moviesmanager.model.Movie

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val movieslList: MutableList<Movie> = mutableListOf()

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        supportActionBar?.subtitle = "Lista de Filmes"

        populaListaMovies()

        movieAdapter = MovieAdapter(this, movieslList)
        amb.moviesLv.adapter = movieAdapter
    }

    private fun populaListaMovies(){
        movieslList.add(
            Movie(1, "Assalto ao banco central", "2011", null, "Fox filmes", 101, false, null, "Ação")
        )
        movieslList.add(
            Movie(2, "O Pequenino", "2006", null, "Columbia Pictures", 98, false, null, "Comédia")
        )
        movieslList.add(
            Movie(1, "Hannibal", "2001", null, "Universal Studios", 131, false, null, "Crime")
        )
    }
}