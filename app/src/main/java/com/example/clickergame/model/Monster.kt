package com.example.clickergame.model

import android.graphics.drawable.Icon

data class Monster(
    var name:String = "",
    var maxHealth: Int = 0,
    var actualHealth: Int = 0,
    var iconRes: Int)
