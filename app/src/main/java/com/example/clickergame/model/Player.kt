package com.example.clickergame.model

data class Player(
    var name:String = "",
    var money:Int = 0,
    var score:Int = 0,
    var health:Int = 0,
    var abilities: Abilities
)
