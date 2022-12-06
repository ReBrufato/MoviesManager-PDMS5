package com.example.moviesmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.moviesmanager.R
import com.example.moviesmanager.adapters.MovieAdapter
import com.example.moviesmanager.controller.MovieRoomController
import com.example.moviesmanager.databinding.ActivityMainBinding
import com.example.moviesmanager.model.Constants.EXTRA_MOVIE
import com.example.moviesmanager.model.Constants.VIEW_MOVIE
import com.example.moviesmanager.model.entity.Movie
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    //a ordenação será pelo nome por default (variável publica)
    var tipoOrdenacao: String = "nome"

    private val movieslList: MutableList<Movie> = mutableListOf()

    private lateinit var movieAdapter: MovieAdapter

    private lateinit var marl: ActivityResultLauncher<Intent>

    //controller para o banco
    private val movieController: MovieRoomController by lazy {
        MovieRoomController(this, tipoOrdenacao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        supportActionBar?.subtitle = "Lista de Filmes"

        populaListaMovies(tipoOrdenacao)

        movieAdapter = MovieAdapter(this, movieslList)
        amb.moviesLv.adapter = movieAdapter

        marl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ){result ->
            if(result.resultCode == RESULT_OK){
                val movie = result.data?.getParcelableExtra<Movie>(EXTRA_MOVIE)

                movie?.let {_movie->
                    val position = movieslList.indexOfFirst { it.id == _movie.id }
                    val verificaNome = movieslList.indexOfFirst { it.nome == _movie.nome }

                    if(position != -1) {
                        movieController.editMovie(_movie)
                    } else{
                        if(verificaNome == -1){
                            movieController.insertMovie(_movie)
                        }else{
                            Toast.makeText(this, "Filme já existe na lista !!!", Toast.LENGTH_LONG).show()
                        }

                    }
                    movieAdapter.notifyDataSetChanged()
                }
            }
        }

        registerForContextMenu(amb.moviesLv)

        amb.moviesLv.onItemClickListener = AdapterView.OnItemClickListener{ _, _, position, _ ->
            val movie = movieslList[position]
            val integralIntent = Intent(this@MainActivity, MovieActivity::class.java)
            integralIntent.putExtra(EXTRA_MOVIE, movie)
            integralIntent.putExtra(VIEW_MOVIE, true)
            startActivity(integralIntent)
        }
    }

    //Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.addMovie -> {
                val intentIntegralActivity = Intent(this,MovieActivity::class.java)
                marl.launch(intentIntegralActivity)
                true
            }R.id.ordenarNome ->{
                tipoOrdenacao = "nome"
                populaListaMovies("nome")
                true
            }R.id.ordenarNota ->{
                tipoOrdenacao = "nota"
                populaListaMovies("nota")
                true
            }
            else -> {false}
        }
    }

    //Menu de contexto
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        val movie = movieslList[position]

        return when(item.itemId) {
            R.id.removeMovie -> {
                movieController.removeMovie(movie)
                true
            }R.id.editMovie ->{
                val movieIntent = Intent(this, MovieActivity::class.java)
                movieIntent.putExtra(EXTRA_MOVIE, movie)
                movieIntent.putExtra(VIEW_MOVIE, false)
                marl.launch(movieIntent)
                true
            }
            else -> {false}
        }
    }

    //popula com banco ou mocado
    private fun populaListaMovies(tipoOrdenacao: String){
        movieController.getMovies(tipoOrdenacao)

//        movieslList.add(
//            Movie(1, "Assalto ao banco central", "2011", null, "Fox filmes", "101", false, null, "Ação")
//        )
//        movieslList.add(
//            Movie(2, "O Pequenino", "2006", null, "Columbia Pictures", "98", false, null, "Comédia")
//        )
//        movieslList.add(
//            Movie(3, "Hannibal", "2001", null, "Universal Studios", "131", true, 7.0, "Crime")
//        )

        var movie1 = Movie(1, "Assalto ao banco central", "2011", null, "Fox filmes", "101", false, null, "Ação")
        var movie2 = Movie(2, "O Pequenino", "2006", null, "Columbia Pictures", "98", false, null, "Comédia")
        var movie3 = Movie(3, "Hannibal", "2001", null, "Universal Studios", "131", true, 7.0, "Crime")
    //    movieController.insertMovie(movie1)
//        movieController.insertMovie(movie2)
//        movieController.insertMovie(movie3)
    }

    //limpa lista de filmes e popula de novo com dados do banco
    fun atualizaListaFilmes(_movieList: MutableList<Movie>){
        movieslList.clear()
        movieslList.addAll(_movieList)
        movieAdapter.notifyDataSetChanged()
    }
}