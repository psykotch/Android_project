package fr.epita.androidproject.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayerViewModel : ViewModel() {

    var PV : MutableLiveData<Int> = MutableLiveData(10)
    var PE : MutableLiveData<Int> = MutableLiveData(10)
    var nbDice : MutableLiveData<Int> = MutableLiveData(1)
    var isKing : MutableLiveData<Boolean> = MutableLiveData(false)
}