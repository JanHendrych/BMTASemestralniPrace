package com.example.clickergame.model

data class Abilities(
    var attack:Int = 1,
    var health:Int = 10,
    var luck:Int = 1,
    var passive:Boolean = false,
    var passiveSpeed:Int = 1
)
