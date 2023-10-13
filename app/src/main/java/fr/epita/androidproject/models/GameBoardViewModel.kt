package fr.epita.androidproject.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameBoardViewModel : ViewModel() {
    var player: MutableLiveData<PlayerViewModel> = MutableLiveData(PlayerViewModel())
    var aIPlayer: MutableLiveData<Array<AIPlayerViewModel>> =
        MutableLiveData(arrayOf(AIPlayerViewModel(), AIPlayerViewModel(), AIPlayerViewModel()))
    var shopCards: MutableLiveData<ArrayList<CardViewModel>> = MutableLiveData(ArrayList())
    var isStarted : MutableLiveData<Boolean> = MutableLiveData(false)
    //var aIPlayers : Array<AIPlayerViewModel> = arrayOf(AIPlayerViewModel(), AIPlayerViewModel(), AIPlayerViewModel())
    //    var cards = ArrayList<CardViewModel>()
    // var usedCards = ArrayList<CardViewModel>()
}