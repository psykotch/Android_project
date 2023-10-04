package fr.epita.androidproject.models

import kotlin.random.Random

class Dice {
    var face = arrayOf("un", "deux", "trois", "vie", "tatane", "energie")

    fun throwDice(): String? {
        return face.random()
    }
}