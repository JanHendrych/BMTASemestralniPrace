package com.example.clickergame.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.clickergame.R

class NewGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)


        var btnStartGame = findViewById<Button>(R.id.btnStart)

        btnStartGame.setOnClickListener(){
            var name = findViewById<TextView>(R.id.editTextPlayerName)


        }
    }
}