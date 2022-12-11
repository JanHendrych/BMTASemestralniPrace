package com.example.clickergame.view

import android.content.Intent
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

            val intent = Intent(this, GameActivity::class.java).also {
                it.putExtra("heroName", name.text.toString())
                startActivity(it)
            }
        }
    }
}