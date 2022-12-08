package com.example.clickergame.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.clickergame.model.Icons
import com.example.clickergame.model.Monster
import kotlin.math.roundToInt

class Game : ViewModel() {
    var activeMonster : Monster = Monster("Peter",10,10, generateRandomImage())



    fun gameClick(){
        activeMonster.actualHealth--
        if (activeMonster.actualHealth == 0){
            generateStrongerMonster()
        }


    }

    fun generateStrongerMonster() {
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