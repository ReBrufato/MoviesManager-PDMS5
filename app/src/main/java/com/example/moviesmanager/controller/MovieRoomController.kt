package com.example.moviesmanager.controller

import android.os.AsyncTask
import androidx.room.Room
import com.example.moviesmanager.model.dao.MovieRoomDao
import com.example.moviesmanager.model.dao.MovieRoomDao.Constant.MOVIE_DATABASE_FILE
import com.example.moviesmanager.model.database.MovieRoomDaoDatabase
import com.example.moviesmanager.model.entity.Movie
import com.example.moviesmanager.view.MainActivity

class MovieRoomController(private val mainActivity: MainActivity) {

    private val movieDaoImpl: MovieRoomDao by lazy {
        Room.databaseBuilder(
            mainActivity,
            MovieRoomDaoDatabase::class.java,
            MOVIE_DATABASE_FILE
        ).build().getMovieRoomDao()
    }

    fun insertMovie(movie: Movie, tipoOrdenacao: String) {
        Thread{
            movieDaoImpl.createMovie(movie)
            getMovies(tipoOrdenacao)
        }.start()
    }

    fun getMovies(tipoOrdenacao: String) {
        object : AsyncTask<Unit, Unit, MutableList<Movie>>() {

            override fun doInBackground(vararg p0: Unit?): MutableList<Movie> {
                val returnList = mutableListOf<Movie>()
                if(tipoOrdenacao == "nome"){
                    returnList.addAll(movieDaoImpl.retrieveMoviesOrderName())
                }else{
                    returnList.addAll(movieDaoImpl.retrieveMoviesOrderNota())
                }
                return returnList
            }

            override fun onPostExecute(result: MutableList<Movie>?) {
                super.onPostExecute(result)
                if(result != null){
                    mainActivity.atualizaListaFilmes(result)
                }
            }
        }.execute()
    }

    fun getMovie(id: Int) = movieDaoImpl.retrieveMovie(id)

    fun editMovie(movie: Movie, tipoOrdenacao: String) {
        Thread{
            movieDaoImpl.updateMovie(movie)
            getMovies(tipoOrdenacao)
        }.start()
    }

    fun removeMovie(movie: Movie, tipoOrdenacao: String) {
        Thread{
            movieDaoImpl.deleteMovie(movie)
            getMovies(tipoOrdenacao)
        }.start()
    }
}