package com.example.clickergame.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.JsonWriter
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.marginLeft
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clickergame.R
import com.example.clickergame.model.ShopItemModel
import com.example.clickergame.viewmodel.Game
import com.example.clickergame.viewmodel.GameFactory
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.Writer
import java.util.LinkedList
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var game : Game
    private val shopItems = LinkedList<ShopItemModel>()

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

        var recyclerView: RecyclerView = findViewById(R.id.recylerViewShop)
        setUpShopItems()
        var adapter:ShopAdapter = ShopAdapter(this, shopItems)
       recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

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

            Toast.makeText(this,"1",Toast.LENGTH_LONG).show()
            saveToJson("test")
        }
    }

    override fun onStop() {
        super.onStop()

        var json = JSONObject()
        json.put("player", game.generateJsonUser())

        saveToJson(json.toString())
    }

    private fun saveToJson(jsonString: String) {
        Toast.makeText(this,"2",Toast.LENGTH_LONG).show()

        val output:Writer

        var filePath = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        filePath?.mkdir()

        val file = File.createTempFile("score", ".json", filePath)

        output = BufferedWriter(FileWriter(file))
        output.write(jsonString)
        output.close()
    }

    private fun setUpShopItems(){
        var itemsTittle = arrayOf<String>(*resources.getStringArray(R.array.shop_items_tittle))
        var itemsDespriction = arrayOf<String>(*resources.getStringArray(R.array.shop_items_desc))
        for (i in  0..itemsTittle.size-1){
            shopItems.add(ShopItemModel(itemsTittle[i], itemsDespriction[i], 0, R.drawable.sword ))
        }

    }
}