package com.example.clickergame.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.clickergame.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var startBtn = findViewById<Button>(R.id.btnNewGame)
        var contBtn = findViewById<Button>(R.id.btnContinue)
        var statsBtn = findViewById<Button>(R.id.btnStats)

        //Pokracovat ve hre
        contBtn.setOnClickListener(){
            val i= Intent(this, GameActivity::class.java)
            startActivity(i)
        }

        //zacit novou hru
        startBtn.setOnClickListener(){
            val i= Intent(this, NewGameActivity::class.java)
            startActivity(i)
        }



    }
}