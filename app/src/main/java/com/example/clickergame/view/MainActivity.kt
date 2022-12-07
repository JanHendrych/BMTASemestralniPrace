package com.example.clickergame.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.clickergame.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var startBtn = findViewById<Button>(R.id.btnStartGame)

        startBtn.setOnClickListener(){
            val i= Intent(this, GameActivity::class.java)
            startActivity(i)
        }
    }
}