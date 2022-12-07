package com.example.clickergame.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.clickergame.R

class GameActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var clicks = findViewById<TextView>(R.id.healthTxt)
        clicks.text = "0"

        var monsterIMG = findViewById<ImageView>(R.id.monsterImage)
        monsterIMG.setImageResource(R.drawable.ic_launcher_background)

        var monsterHealth = findViewById<ProgressBar>(R.id.monsterHealth)
        monsterHealth.max = 20

        monsterIMG.setOnClickListener(){
            var numberOfClicks = 0
            numberOfClicks = Integer.parseInt(clicks.text.toString())
            numberOfClicks++
            clicks.text = numberOfClicks.toString()

            monsterHealth.setProgress(numberOfClicks,true)
        }

    }
}