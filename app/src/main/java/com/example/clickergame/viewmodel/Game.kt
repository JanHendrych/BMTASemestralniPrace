package com.example.clickergame.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.clickergame.model.Icons
import com.example.clickergame.model.Monster
import com.example.clickergame.model.Player
import org.json.JSONObject
import kotlin.math.roundToInt
import kotlin.random.Random

class Game : ViewModel() {
    var activeMonster : Monster = Monster("Peter",10,10, generateRandomImage())
    var player: Player = Player("Jan", 0, 0)

    fun gameClick(){
        activeMonster.actualHealth--
        if (activeMonster.actualHealth == 0){
            generateStrongerMonster()
            player.money += generateRandomCoins()
        }
    }

    fun generateStrongerMonster() {
        player.score++

        activeMonster.name = "Thomas"
        var newHealth = activeMonster.maxHealth * 1.5
        activeMonster.maxHealth = newHealth.roundToInt()
        activeMonster.actualHealth = newHealth.roundToInt()
        activeMonster.iconRes = generateRandomImage()
    }

    //GENEROVANI NAHODNEHO OBRAZKU
    private fun generateRandomImage():Int{
       return Icons.values().random().imgResource
    }

    //GENEROVANI PENEZ ZA ZABITI
    private fun generateRandomCoins():Int{
        return Random.nextInt(1, 100)
    }

    fun generateJsonUser(): JSONObject {
        return JSONObject()
            .put("username", player.name)
            .put("money", player.money)
            .put("score", player.score)
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