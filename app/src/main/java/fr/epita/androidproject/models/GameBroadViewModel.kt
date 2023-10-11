package fr.epita.androidproject.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameBroadViewModel : ViewModel() {
    var player: MutableLiveData<PlayerViewModel> = MutableLiveData(PlayerViewModel())
    var aIPlayer: MutableLiveData<Array<AIPlayerViewModel>> =
        MutableLiveData(arrayOf(AIPlayerViewModel(), AIPlayerViewModel(), AIPlayerViewModel()))

    var cards: MutableLiveData<ArrayList<CardViewModel>> = MutableLiveData(ArrayList())
    var usedCards: MutableLiveData<ArrayList<CardViewModel>> = MutableLiveData(ArrayList())
    //var aIPlayers : Array<AIPlayerViewModel> = arrayOf(AIPlayerViewModel(), AIPlayerViewModel(), AIPlayerViewModel())
    //    var cards = ArrayList<CardViewModel>()
    // var usedCards = ArrayList<CardViewModel>()
}