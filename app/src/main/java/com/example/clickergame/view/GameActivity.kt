package com.example.clickergame.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.marginLeft
import androidx.lifecycle.ViewModelProvider
import com.example.clickergame.R
import com.example.clickergame.viewmodel.Game
import com.example.clickergame.viewmodel.GameFactory
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private lateinit var game : Game
    private var playerName:String = "player"

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //viewModel
        val factory = GameFactory()
        game = ViewModelProvider(this, factory).get(Game::class.java)

        //Jmeno hrdiny
        game.player.name = intent.getStringExtra("heroName").toString()
        findViewById<TextView>(R.id.heroNameTxt).text = game.player.name

        //Prvni prisera
        var actualHealth = findViewById<TextView>(R.id.actualHealthTxt)
        var maxHealth = findViewById<TextView>(R.id.maxHealthTxt)
        var coins = findViewById<TextView>(R.id.textWiCoins)
        actualHealth.text = game.activeMonster.actualHealth.toString()
        maxHealth.text = game.activeMonster.maxHealth.toString()

        //Prisera obrazek
        var monsterIMG = findViewById<ImageView>(R.id.monsterImage)
        monsterIMG.setImageResource(game.activeMonster.iconRes)


        var monsterHealth = findViewById<ProgressBar>(R.id.monsterHealth)
        monsterHealth.max = game.activeMonster.maxHealth


        //Kliknuti na priseru
        monsterIMG.setOnClickListener(){
            game.gameClick()
            monsterIMG.requestFocus()
            monsterHealth.setProgress(game.activeMonster.maxHealth - game.activeMonster.actualHealth,true)
            monsterHealth.max = game.activeMonster.maxHealth
            actualHealth.text = game.activeMonster.actualHealth.toString()
            maxHealth.text = game.activeMonster.maxHealth.toString()
            monsterIMG.setImageResource(game.activeMonster.iconRes)
            coins.text = game.player.money.toString()
        }

    }
}