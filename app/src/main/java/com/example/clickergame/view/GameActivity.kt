package com.example.clickergame.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clickergame.R
import com.example.clickergame.model.ShopItemModel
import com.example.clickergame.viewmodel.Game
import com.example.clickergame.viewmodel.GameFactory
import com.google.gson.Gson
import org.json.JSONObject
import java.io.*
import java.util.*


class GameActivity : AppCompatActivity(), RecyclerViewInterface {

    private lateinit var game : Game
    private var shopItems = LinkedList<ShopItemModel>()
    private lateinit var adapter:ShopAdapter
    private lateinit var coins:TextView
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
        val actualHealth = findViewById<TextView>(R.id.actualHealthTxt)
        val maxHealth = findViewById<TextView>(R.id.maxHealthTxt)
        val playerHealth = findViewById<TextView>(R.id.tvPlayerHealth)
        coins = findViewById<TextView>(R.id.textWiCoins)
        actualHealth.text = game.activeMonster.actualHealth.toString()
        maxHealth.text = game.activeMonster.maxHealth.toString()
        game.player.score = 1

        //Prisera obrazek
        val monsterIMG = findViewById<ImageView>(R.id.monsterImage)
        monsterIMG.setImageResource(game.activeMonster.iconRes)


        val monsterHealth = findViewById<ProgressBar>(R.id.monsterHealth)
        monsterHealth.max = game.activeMonster.maxHealth

        if (intent.getStringExtra("continue").toString() == "true"){
            game.player.money = intent.getIntExtra("penize",1)
            game.player.abilities.attack = intent.getIntExtra("silaUtoku",1)
            game.player.abilities.health = intent.getIntExtra("zivoty2",20)
            game.player.abilities.passive = intent.getStringExtra("pasivniUtok").toString() == "true"
            game.player.abilities.passiveSpeed = intent.getIntExtra("PasivniUtokRychlost",1)
            game.player.score = intent.getIntExtra("Score",1)
            game.player.health = intent.getIntExtra("zivoty",1)

            for (i in 0 until game.player.score-2){
                game.generateStrongerMonster()
            }
        }

        //Obchod
        val recyclerView: RecyclerView = findViewById(R.id.recylerViewShop)

        setUpShopItems()
        game.shopItems = shopItems
        adapter = ShopAdapter(this, shopItems, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Uložit a ukončit
        val btnSave = findViewById<Button>(R.id.btnSave)

        //Kliknuti na priseru
        monsterIMG.setOnClickListener(){
            game.gameClick()
            playerHealth.text = game.player.health.toString()
            monsterIMG.requestFocus()
            monsterHealth.setProgress(game.activeMonster.maxHealth - game.activeMonster.actualHealth,true)
            monsterHealth.max = game.activeMonster.maxHealth
            actualHealth.text = game.activeMonster.actualHealth.toString()
            maxHealth.text = game.activeMonster.maxHealth.toString()
            monsterIMG.setImageResource(game.activeMonster.iconRes)
            coins.text = game.player.money.toString()
            shopItems = game.shopItems
            adapter.notifyDataSetChanged()

        }

        btnSave.setOnClickListener(){
            saveToJson(game.generateJsonUser())
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
    }

    private fun saveToJson(jsonString: JSONObject) {
        val jsonString = Gson().toJson(game.player)
        baseContext.openFileOutput("score.json", Context.MODE_PRIVATE).use {
            it.write(jsonString.toByteArray())
        }
    }

    private fun setUpShopItems(){
        val itemsTittle = arrayOf<String>(*resources.getStringArray(R.array.shop_items_tittle))
        val itemsDespriction = arrayOf<String>(*resources.getStringArray(R.array.shop_items_desc))
        val itemsPrices = arrayOf<String>(*resources.getStringArray(R.array.shop_items_prices))

        for (i in  0..itemsTittle.size-1){
            shopItems.add(ShopItemModel(itemsTittle[i], itemsDespriction[i], itemsPrices[i].toInt(), R.drawable.sword ))
        }

    }

    override fun onItemClick(position: Int) {
        val check:Boolean = game.buyStuffFromShop(position)
        if (check == false){
            Toast.makeText(this, "Not enough coins", Toast.LENGTH_SHORT).show()
        }else{
            adapter.notifyDataSetChanged()
            coins.text = game.player.money.toString()
        }
    }
}