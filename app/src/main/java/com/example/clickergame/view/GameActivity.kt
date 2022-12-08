package com.example.clickergame.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.clickergame.R
import com.example.clickergame.viewmodel.Game
import com.example.clickergame.viewmodel.GameFactory

class GameActivity : AppCompatActivity() {
    private lateinit var game : Game

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //viewModel
        val factory = GameFactory()
        game = ViewModelProvider(this, factory).get(Game::class.java)

        //Prvni prisera
        var actualHealth = findViewById<TextView>(R.id.actualHealthTxt)
        var maxHealth = findViewById<TextView>(R.id.maxHealthTxt)
        actualHealth.text = game.activeMonster.actualHealth.toString()
        maxHealth.text = game.activeMonster.maxHealth.toString()

        //Prisera obrazek
        var monsterIMG = findViewById<ImageView>(R.id.monsterImage)
        monsterIMG.setImageResource(R.drawable.ic_launcher_background)

        var monsterHealth = findViewById<ProgressBar>(R.id.monsterHealth)
        monsterHealth.max = game.activeMonster.maxHealth

        //Kliknuti na priseru
        monsterIMG.setOnClickListener(){
            game.gameClick()
            monsterHealth.setProgress(game.activeMonster.maxHealth - game.activeMonster.actualHealth,true)
            monsterHealth.max = game.activeMonster.maxHealth

            actualHealth.text = game.activeMonster.actualHealth.toString()
            maxHealth.text = game.activeMonster.maxHealth.toString()
        }

    }
}