package fr.epita.androidproject.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CardViewModel : ViewModel() {

    var effect : MutableLiveData<CardEffect> = MutableLiveData()
    var isImmediate : MutableLiveData<Boolean> = MutableLiveData()
    var price : MutableLiveData<Int> = MutableLiveData()
}