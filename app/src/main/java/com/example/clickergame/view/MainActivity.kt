package com.example.clickergame.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.clickergame.R
import org.json.JSONException
import org.json.JSONObject
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startBtn = findViewById<Button>(R.id.btnNewGame)
        val contBtn = findViewById<Button>(R.id.btnContinue)
        val statsBtn = findViewById<Button>(R.id.btnStats)

        //Pokracovat ve hre
        contBtn.setOnClickListener(){

            val playerText = readFileJson()

            val abilities = parseJsonString(playerText, "abilities").toString()

            val intent = Intent(this, GameActivity::class.java).also {
                it.putExtra("continue", "true")
                it.putExtra("heroName", parseJsonString(playerText, "name"))
                it.putExtra("zivoty", parseJsonInt(playerText, "health"))
                it.putExtra("penize", parseJsonInt(playerText, "money"))
                it.putExtra("silaUtoku", parseJsonInt(abilities, "attack"))
                it.putExtra("zivoty2", parseJsonInt(abilities, "health"))
                it.putExtra("stesti", parseJsonInt(abilities, "luck"))
                it.putExtra("pasivniUtok", parseJsonString(abilities, "passive"))
                it.putExtra("PasivniUtokRychlost", parseJsonInt(abilities, "passiveSpeed"))
                it.putExtra("Score", parseJsonInt(playerText, "score"))
                startActivity(it)
            }
        }

        //zacit novou hru
        startBtn.setOnClickListener(){
            val i= Intent(this, NewGameActivity::class.java)
            startActivity(i)
        }

        //Statistiky
        statsBtn.setOnClickListener(){
            val i= Intent(this, Statistics::class.java)
            startActivity(i)
        }

    }

    fun readFileJson () : String {
        val file = File(baseContext.filesDir, "score.json")
        return file.readText()
    }

    fun parseJsonInt(settingsJson:String, item:String) : Int? {
        try {
            val jObj = JSONObject(settingsJson)
            return jObj.getInt(item)
        } catch (ex: JSONException) {
            Log.i("JsonParser Int", "unexpected JSON exception", ex)
            return null
        }
    }
    fun parseJsonString(settingsJson:String, item:String) : String? {
        try {
            val jObj = JSONObject(settingsJson)
            return jObj.getString(item)
        } catch (ex: JSONException) {
            Log.i("JsonParser Int", "unexpected JSON exception", ex)
            return null
        }
    }
}