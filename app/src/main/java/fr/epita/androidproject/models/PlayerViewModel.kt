package fr.epita.androidproject.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Hashtable

open class PlayerViewModel : ViewModel() {

    var life : MutableLiveData<Int> = MutableLiveData(10)
    var energy : MutableLiveData<Int> = MutableLiveData(0)
    var score: MutableLiveData<Int> = MutableLiveData(0)
    var nbDice : MutableLiveData<Int> = MutableLiveData(6)
    var isKing : MutableLiveData<Boolean> = MutableLiveData(false)
    var permanentCards : MutableLiveData<CardViewModel> = MutableLiveData()
    var immediateCards : MutableLiveData<CardViewModel> = MutableLiveData()
}