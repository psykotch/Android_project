package fr.epita.androidproject.models

import androidx.lifecycle.ViewModel
import java.util.UUID

class CardViewModel : ViewModel() {
    private var typeArray: Array<String> = arrayOf(
        "life",
        "energy",
        "victory"
    )

    val uuid = UUID.randomUUID()
    var type : String = typeArray.random()
    var effect : Int = (1..3).random()
    var isImmediate : Boolean = true
    var price : Int = this.effect * 2
}