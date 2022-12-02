package com.example.moviesmanager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.moviesmanager.databinding.ActivityMovieBinding
import com.example.moviesmanager.model.Constants.EXTRA_MOVIE
import com.example.moviesmanager.model.Constants.VIEW_MOVIE
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

        //se a intent estiver vazia significa que a tela foi chamada para criar um novo filme
        val receivedMovie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        val viewMovie = intent.getBooleanExtra(VIEW_MOVIE,false)
        receivedMovie?.let{_receivedMovie ->
            with(_receivedMovie){
                amb.nomeEtMv.setText(nome)
                amb.anoLancamentoEtMv.setText(anoLancamento)

                //tratamento estúdio
                if (estudio == null){
                    amb.estudioEtMv.setText("Estúdio não informado")
                }else{
                    amb.estudioEtMv.setText(estudio)
                }

                //tratamento produtora
                if (produtora == null){
                    amb.produtoraEtMv.setText("Produtora não informada")
                }else{
                    amb.produtoraEtMv.setText(produtora)
                }

                amb.duracaoEtMv.setText(duracao.toString() + " min")

                //tratamento da flag e nota
                if(flag == true){
                    //flag
                    amb.checkFlagMv.isChecked = true

                    //nota
                    amb.notaEtMv.visibility = View.VISIBLE
                    amb.notaEtMv.setText(nota.toString())
                }

                //tratamento gênero
                amb.gerenoSpinnerMv.visibility = View.GONE

                amb.generoTextMv.visibility = View.VISIBLE
                amb.generoTextMv.setText(genero)

                //FAZER PARTE DO SPINNER
                amb.salvarMovieBt.visibility = View.GONE

                //para visualizar detalhes, desativa edição

                //trata visualização de detalhes
                if(viewMovie){
                    amb.nomeEtMv.isEnabled = false
                    amb.anoLancamentoEtMv.isEnabled = false
                    amb.estudioEtMv.isEnabled = false
                    amb.produtoraEtMv.isEnabled = false
                    amb.duracaoEtMv.isEnabled = false
                    amb.notaEtMv.isEnabled = false
                    amb.checkFlagMv.isEnabled = false
                    amb.generoTextMv.isEnabled = false
                }

            }
        }



        amb.salvarMovieBt.setOnClickListener {
            var nota: Double?

            //verifica se nota é diferente de vazio
            if (amb.notaEtMv.text.toString() != ""){
                nota = amb.notaEtMv.text.toString().toDouble()
            }else{
                nota = null
            }

            val movie = Movie(
                id = receivedMovie?.id?: Random(System.currentTimeMillis()).nextInt(),
                nome = amb.nomeEtMv.text.toString(),
                anoLancamento = amb.anoLancamentoEtMv.toString(),
                estudio = amb.estudioEtMv.text.toString(),
                produtora = amb.produtoraEtMv.text.toString(),
                duracao = amb.duracaoEtMv.text.toString().toInt(),
                flag = amb.checkFlagMv.isChecked,
                nota = nota,
                genero = amb.gerenoSpinnerMv.toString()
            )

            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_MOVIE, movie)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}