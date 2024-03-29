package com.example.clickergame.viewmodel


import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.clickergame.model.*
import com.example.clickergame.view.GameActivity
import org.json.JSONObject
import java.util.*
import kotlin.math.roundToInt
import kotlin.random.Random


class Game : ViewModel() {
    var activeMonster : Monster = Monster("Peter",10,10, generateRandomImage())
    var player: Player = Player("Jan", 0, 0, 20,Abilities())
    var shopItems = LinkedList<ShopItemModel>()
    var gameFinished: Boolean = false

    fun gameClick(){
        if (gameFinished){
            return
        }
        activeMonster.actualHealth -= player.abilities.attack
        damage()
        if (activeMonster.actualHealth <= 0){
            generateStrongerMonster()
            player.money += generateRandomCoins()
        }
    }

    fun generateStrongerMonster() {
        player.score++
        activeMonster.iconRes = generateRandomImage()
        if (activeMonster.iconRes.compareTo(Icons.LUCK.imgResource) == 0){
            generateStrongerMonster()
        }else{
            activeMonster.name = "Thomas"
            val newHealth = activeMonster.maxHealth * 1.5
            activeMonster.maxHealth = newHealth.roundToInt()
            activeMonster.actualHealth = newHealth.roundToInt()
            player.health += Random.nextInt(1, 10)
        }
    }
    fun generateStrongerMonsterContinue() {
        activeMonster.iconRes = generateRandomImage()
        if (activeMonster.iconRes.compareTo(Icons.LUCK.imgResource) == 0){
            generateStrongerMonster()
        }else{
            activeMonster.name = "Thomas"
            val newHealth = activeMonster.maxHealth * 1.5
            activeMonster.maxHealth = newHealth.roundToInt()
            activeMonster.actualHealth = newHealth.roundToInt()
        }
    }
//Nakup vybaveni
    fun buyStuffFromShop(position: Int):Boolean{
        val enough:Boolean
        if(player.money < shopItems.get(position).price){
          enough = false
        }else{
            buy(position)
            player.money -= shopItems.get(position).price
            shopItems.get(position).price += 20
            enough = true
            println(player.abilities.toString())
        }
        return enough
    }

    fun buy(position: Int){
        when(position){
            0 -> player.abilities.attack++
            1 -> player.health += 10
            2 -> player.abilities.luck++
            3 -> player.abilities.passive = true
            4 -> player.abilities.passiveSpeed++
        }
    }

    fun damage(){
     if(Random.nextInt(1, 35) <= 4){
         var damage:Double = Math.sqrt(player.score.toDouble())
         player.health -= Math.ceil(damage).toInt()
     }
    }


    //GENEROVANI NAHODNEHO OBRAZKU
    private fun generateRandomImage():Int{
       return Icons.values().random().imgResource
    }

    //GENEROVANI PENEZ ZA ZABITI
    private fun generateRandomCoins():Int{
        if(Random.nextInt(1, 35) <= (player.abilities.luck + 3)){
            return Random.nextInt(100, 200)
        }
        return Random.nextInt(20, 100)
    }

    fun generateJsonUser(): JSONObject {
        return JSONObject()
            .put("username", player.name)
            .put("money", player.money)
            .put("score", player.score)
    }

    fun passiveAttack() {
        if (gameFinished){
            return
        }
        if (player.abilities.passive){
            activeMonster.actualHealth -= player.abilities.passiveSpeed
            if (activeMonster.actualHealth <= 0){
                generateStrongerMonster()
                player.money += generateRandomCoins()
            }
        }
    }
}

class GameFactory (

): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Game::class.java)) {
            return Game() as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}