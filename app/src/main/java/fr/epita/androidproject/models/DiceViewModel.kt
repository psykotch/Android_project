package fr.epita.androidproject.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class DiceViewModel : ViewModel() {

    enum class diceFace {
        ONE, TWO, THREE, ENERGY, ATTACK, LIFE
    }

    var diceArray : MutableLiveData<Array<diceFace>>  = MutableLiveData(arrayOf(diceFace.ONE, diceFace.TWO, diceFace.THREE, diceFace.ENERGY, diceFace.ATTACK, diceFace.LIFE))

    private fun roll(): diceFace {
        return diceArray.value?.random() ?:diceFace.LIFE;
    }
}