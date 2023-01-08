package com.example.clickergame.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.clickergame.R
import org.json.JSONException
import org.json.JSONObject
import java.io.*

class Statistics : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        val textView = findViewById<ListView>(R.id.playerTxtView)

        val playerText = readFileJson()

        val abilities = parseJsonString(playerText, "abilities").toString()

        val arrayAdapter: ArrayAdapter<*>
        val player = arrayOf(
            String.format("%35s %10s","Jméno hráče:", parseJsonString(playerText, "name")),
            String.format("%35s %10s","Životy:", parseJsonString(playerText, "name")),
            String.format("%35s %10s","Peníze:", parseJsonInt(playerText, "money")),
            String.format("%35s %10s","Síla útoku:", parseJsonInt(abilities, "attack")),
            String.format("%35s %10s","Životy:", parseJsonInt(abilities, "health")),
            String.format("%35s %10s","Štěstí:", parseJsonInt(abilities, "luck")),
            String.format("%35s %10s","Pasivní útok:", parseJsonString(abilities, "passive")),
            String.format("%35s %10s","Pasivní útok rychlost:", parseJsonInt(abilities, "passiveSpeed")),
            String.format("%35s %10s","Score:", parseJsonInt(playerText, "score"))
        )

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, player)

        textView.adapter = arrayAdapter
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