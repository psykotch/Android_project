package fr.epita.androidproject.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Hashtable

open class PlayerViewModel : ViewModel() {

    var name: MutableLiveData<String> = MutableLiveData("Unknown")
    var life: MutableLiveData<Int> = MutableLiveData(10)
    var energy: MutableLiveData<Int> = MutableLiveData(0)
    var score: MutableLiveData<Int> = MutableLiveData(0)
    var nbDice: MutableLiveData<Int> = MutableLiveData(6)
    var isKing: MutableLiveData<Boolean> = MutableLiveData(false)
    var permanentCards: MutableLiveData<ArrayList<CardViewModel>> = MutableLiveData(ArrayList())
    var immediateCards: MutableLiveData<ArrayList<CardViewModel>> = MutableLiveData(ArrayList())
    var lastDicedValues: MutableLiveData<String> = MutableLiveData("")
}