package fr.epita.androidproject.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class DiceViewModel : ViewModel() {

    enum class diceFace {
        ONE, TWO, THREE, ENERGY, ATTACK, LIFE
    }

    var diceArray: Array<diceFace> = arrayOf(
        diceFace.ONE,
        diceFace.TWO,
        diceFace.THREE,
        diceFace.ENERGY,
        diceFace.ATTACK,
        diceFace.LIFE
    )

    fun roll(): Array<diceFace> {

        return arrayOf(
            diceArray.random(),
            diceArray.random(),
            diceArray.random(),
            diceArray.random(),
            diceArray.random(),
            diceArray.random()
        )
    }
}