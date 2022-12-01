package com.example.moviesmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.moviesmanager.databinding.ActivityMovieBinding
import com.example.moviesmanager.model.Constants.EXTRA_MOVIE
import com.example.moviesmanager.model.Movie
import kotlin.random.Random


class MovieActivity : AppCompatActivity() {

    private val amb: ActivityMovieBinding by lazy {
        ActivityMovieBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        //verifica se o 'já assisti' está checado, para exibir a nota
        amb.checkFlagMv.setOnClickListener() {
            if (amb.checkFlagMv.isChecked) {
                amb.notaEtMv.visibility = View.VISIBLE
            }else{
                amb.notaEtMv.visibility = View.GONE
            }
        }

        val receivedMovie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        receivedMovie?.let{_receivedMovie ->
            with(_receivedMovie){
                amb.nomeEtMv.setText(nome)
                amb.anoLancamentoEtMv.setText(anoLancamento)
                amb.estudioEtMv.setText(estudio)
                amb.produtoraEtMv.setText(produtora)
                amb.duracaoEtMv.setText(duracao.toString())
                if(flag == true){
                    amb.checkFlagMv.isChecked
                }
            }
        }

        amb.salvarMovieBt.setOnClickListener {
            val movie = Movie(
                id = receivedMovie?.id?: Random(System.currentTimeMillis()).nextInt(),
                nome = amb.nomeEtMv.text.toString(),
                anoLancamento = amb.anoLancamentoEtMv.toString(),
                estudio = amb.estudioEtMv.text.toString(),
                produtora = amb.produtoraEtMv.text.toString(),
                duracao = amb.duracaoEtMv.text.toString().toInt(),
                flag = amb.checkFlagMv.isChecked,
                nota = amb.notaEtMv.text.toString().toDouble(),
                genero = amb.gerenoSpinnerMv.toString()
            )

            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_MOVIE, movie)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}