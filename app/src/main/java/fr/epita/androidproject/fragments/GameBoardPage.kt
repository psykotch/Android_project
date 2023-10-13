package fr.epita.androidproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fr.epita.androidproject.R
import fr.epita.androidproject.models.AIPlayerViewModel
import fr.epita.androidproject.models.GameBoardViewModel
import fr.epita.androidproject.models.PlayerViewModel
import fr.epita.androidproject.utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameBoardPage : Fragment() {
    private val args: GameBoardPageArgs by navArgs()
    private val gameBoardViewModel: GameBoardViewModel by activityViewModels()

    private val aiPlayersViewDetailsButton: ArrayList<ImageButton> = ArrayList();
    private val aiPlayersVictory: ArrayList<TextView> = ArrayList()
    private val aiPlayersLife: ArrayList<TextView> = ArrayList()
    private val aiPlayersEnergy: ArrayList<TextView> = ArrayList()
    private val PlayersInfoTextView: ArrayList<TextView> = ArrayList()
    private val PlayersDiceInfoTextView: ArrayList<TextView> = ArrayList()
    private val humanPlayerStatView: HashMap<String, TextView> = HashMap()
    private val playerCardsViewButton: ArrayList<Button> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val humanPlayer = this.gameBoardViewModel.player.value!!
        val aiPlayers = this.gameBoardViewModel.aIPlayer.value!!
        val nextButton: Button = view.findViewById(R.id.nextButton)
        val playButton: Button = view.findViewById(R.id.playButton)
        val shopButton: Button = view.findViewById(R.id.shopButton)
        val playerName: TextView = view.findViewById(R.id.board_player1_name)

        playerName.text = humanPlayer.name.value.toString()

        getPlayersStatView(view)

        if (args.diceFaces != null) {
            val parsedResult = utils().parseDicesFaces(args.diceFaces!!)
            PlayersDiceInfoTextView[0].text = parsingDiceValue(parsedResult.toString())
            playerUpdateAfterRollDice(parsedResult, humanPlayer, nextButton)
        }
        updatePlayerStatView()

        getPlayerCardView(view)
        updatePlayerCardView(humanPlayer)

        initGame(humanPlayer)


        val humanPlayerDetailButton: ImageButton = view.findViewById(R.id.board_player1_image_button)
        humanPlayerDetailButton.setOnClickListener() {
            findNavController().navigate(
                GameBoardPageDirections.actionBoardPageToPlayerDetailPage(0, true)
            )
        }

        nextButton.setOnClickListener() {
            playButton.isVisible = false

            aiPlay(humanPlayer, aiPlayers)

            playButton.isVisible = true
            utils().alert(this.context, "Game Info", "Now is your turn")

            nextButton.isVisible = false
            this.onViewCreated(view, savedInstanceState)

        }

        playButton.setOnClickListener() {
            findNavController().navigate(
                GameBoardPageDirections.actionGameBoardPageToRollDicePage()
            )
        }

        shopButton.setOnClickListener() {
            findNavController().navigate(
                GameBoardPageDirections.actionBoardPageToStorePage()
            )
        }
    }

    private fun updatePlayerCardView(humanPlayer: PlayerViewModel) {
        var count = 0
        for (card in humanPlayer.immediateCards.value!!) {
            val playerCardButton = playerCardsButton[count]
            playerCardButton.text = card.uuid.toString()
            count += 1
        }
    }

    private fun initGame(humanPlayer: PlayerViewModel) {
        if (!this.gameBoardViewModel.isStarted.value!!) {
            this.gameBoardViewModel.isStarted.postValue(true)
            humanPlayer.isKing.postValue(true)
            PlayersInfoTextView[0].text = "👑"
        }
    }

    private fun playerUpdateAfterRollDice(parsedResult: MutableMap<String, Int>,humanPlayer: PlayerViewModel, nextButton: Button) {
            val attack = parsedResult["ATTACK"]
            if (attack != null && attack > 0) {
                if (humanPlayer.isKing.value!!) {
                    for (aiPlayer in this.gameBoardViewModel.aIPlayer.value!!) {
                        aiPlayer.life.postValue(aiPlayer.life.value!!.minus(attack))
                    }
                } else {
                    for (aiPlayer in this.gameBoardViewModel.aIPlayer.value!!) {
                        if (aiPlayer.isKing.value!!)
                            aiPlayer.life.postValue(aiPlayer.life.value!!.minus(attack))
                    }
                }
            }

            val energy = parsedResult["ENERGY"]
            if (energy != null && energy > 0) {
                var currEnergy = this.gameBoardViewModel.player.value?.energy?.value
                this.gameBoardViewModel.player.value?.energy?.postValue(currEnergy?.plus(energy))
            }

            val life = parsedResult["LIFE"]
            if (life != null && life > 0) {
                var currLife = this.gameBoardViewModel.player.value?.life?.value
                this.gameBoardViewModel.player.value?.life?.postValue(currLife?.plus(life))
            }

            val victory = parsedResult["SCORE"]
            if (victory != null && victory > 0) {
                var currVictory = this.gameBoardViewModel.player.value?.score?.value
                this.gameBoardViewModel.player.value?.score?.postValue(currVictory?.plus(victory))
            }

            nextButton.isVisible = true
    }

    private fun getPlayerCardView(view: View) {
        playerCardsButton.add(view.findViewById(R.id.board_card_button_1))
        playerCardsButton.add(view.findViewById(R.id.board_card_button_2))
        playerCardsButton.add(view.findViewById(R.id.board_card_button_3))
        var playerCards = this.gameBoardViewModel.player.value?.immediateCards?.value
        for (i in 0..2) {
            playerCardsViewButton[i].setOnClickListener() {
                if (playerCards != null) {
                    GameBoardPageDirections.actionBoardPageToCardDetailPage(playerCards[i].uuid.toString())
                }
            }
        }
    }

    private fun getPlayersStatView(view: View) {
        aiPlayersViewDetailsButton.add(view.findViewById(R.id.board_player2_image_button))
        aiPlayersViewDetailsButton.add(view.findViewById(R.id.board_player3_image_button))
        aiPlayersViewDetailsButton.add(view.findViewById(R.id.board_player4_image_button))
        for (i in 0..2) {
            aiPlayersViewDetailsButton[i].setOnClickListener() {
                findNavController().navigate(
                    GameBoardPageDirections.actionBoardPageToPlayerDetailPage(i, false)
                )
            }
        }

        aiPlayersVictory.add(view.findViewById(R.id.board_player2_victory_text))
        aiPlayersVictory.add(view.findViewById(R.id.board_player3_victory_text))
        aiPlayersVictory.add(view.findViewById(R.id.board_player4_victory_text))

        aiPlayersLife.add(view.findViewById(R.id.board_player2_life_text))
        aiPlayersLife.add(view.findViewById(R.id.board_player3_life_text))
        aiPlayersLife.add(view.findViewById(R.id.board_player4_life_text))

        aiPlayersEnergy.add(view.findViewById(R.id.board_player2_energy_text))
        aiPlayersEnergy.add(view.findViewById(R.id.board_player3_energy_text))
        aiPlayersEnergy.add(view.findViewById(R.id.board_player4_energy_text))

        PlayersInfoTextView.add(view.findViewById(R.id.board_player1_textview_info))
        PlayersInfoTextView.add(view.findViewById(R.id.board_player2_textview_info))
        PlayersInfoTextView.add(view.findViewById(R.id.board_player3_textview_info))
        PlayersInfoTextView.add(view.findViewById(R.id.board_player4_textview_info))

        PlayersDiceInfoTextView.add(view.findViewById(R.id.board_player1_textview_dice_info))
        PlayersDiceInfoTextView.add(view.findViewById(R.id.board_player2_textview_dice_info))
        PlayersDiceInfoTextView.add(view.findViewById(R.id.board_player3_textview_dice_info))
        PlayersDiceInfoTextView.add(view.findViewById(R.id.board_player4_textview_dice_info))

        humanPlayerStatView["Life"] = view.findViewById(R.id.board_life_text)
        humanPlayerStatView["Victory"] = view.findViewById(R.id.board_victory_text)
        humanPlayerStatView["Energy"] = view.findViewById(R.id.board_energy_text)
    }

    private fun updatePlayerStatView() {
        val aiPlayers = this.gameBoardViewModel.aIPlayer.value
        val humanPlayer = this.gameBoardViewModel.player.value
        for (i in 0..2) {
            aiPlayersVictory[i].text = aiPlayers?.get(i)?.score?.value.toString()
            //aiPlayersLife[i].text = aiPlayers?.get(i)?.life?.value.toString()
            aiPlayersEnergy[i].text = aiPlayers?.get(i)?.energy?.value.toString()
        }
        humanPlayerStatView["Life"]?.text = humanPlayer?.life?.value?.toString()
        humanPlayerStatView["Victory"]?.text = humanPlayer?.score?.value?.toString()
        humanPlayerStatView["Energy"]?.text = humanPlayer?.energy?.value?.toString()
    }

    private fun aiPlay(humanPlayer: PlayerViewModel, aiPlayers: Array<AIPlayerViewModel>) {

        for (i in 0..2) {
            val aiPlayer = aiPlayers[i]
            val aiDicedResults = aiPlayer.makeMove()
            val aiParsedResult = utils().parseDicesFaces(aiDicedResults)
            this.PlayersDiceInfoTextView[i + 1].text = parsingDiceValue((aiParsedResult.toString()))
            val aiAttackCount = aiParsedResult["ATTACK"]
            if (aiAttackCount != null && aiAttackCount > 0) {
                if (aiPlayer.isKing.value!!) {
                    //attack tout le monde
                    humanPlayer.life.postValue(humanPlayer.life.value!!.minus(aiAttackCount))
                    for (aiPlayerLeft in aiPlayers) {
                        if (!aiPlayerLeft.isKing.value!! && aiPlayer != aiPlayerLeft)
                            aiPlayerLeft.life.postValue(aiPlayerLeft.life.value!!.minus(aiAttackCount))
                    }
                } else {
                    //attack the king
                    if (humanPlayer.isKing.value!!) {
                        humanPlayer.life.postValue(humanPlayer.life.value!!.minus(aiAttackCount))
                        utils().alertYesNo(
                            this.context,
                            "Attacked",
                            "Do you want to get out of the tokyo",
                            "Yes",
                            "Hell no",
                            humanPlayer,
                            aiPlayer,
                            ::tokyoGetOut
                        )
                    } else {
                        for (aiPlayerLeft in aiPlayers) {
                            if (aiPlayerLeft.isKing.value!! && aiPlayer != aiPlayerLeft) {
                                aiPlayerLeft.life.postValue(aiPlayerLeft.life.value!!.minus(aiAttackCount))
                                aiPlayerLeft.isKing.postValue(false)
                                aiPlayer.isKing.postValue(true)
                            }
                        }
                    }
                }
            }

            val energy = aiParsedResult["ENERGY"]
            if (energy != null && energy > 0) {
                var currEnergy = aiPlayer.energy.value
                aiPlayer.energy.postValue(currEnergy?.plus(energy))
            }

            val life = aiParsedResult["LIFE"]
            if (life != null && life > 0) {
                var currLife = aiPlayer.life.value
                aiPlayer.energy.postValue(currLife?.plus(life))
            }

            val victory = aiParsedResult["VICTORY"]
            if (victory != null && victory > 0) {
                var currScore = aiPlayer.score.value
                aiPlayer.score.postValue(currScore?.plus(victory))
            }
        }
        checkPlayersLife(humanPlayer, aiPlayers)
        checkKing(humanPlayer, aiPlayers)
    }

    private fun tokyoGetOut(humanPlayer: PlayerViewModel, aiPlayer: AIPlayerViewModel) {
        humanPlayer.isKing.postValue(false)
        aiPlayer.isKing.postValue(true)
    }

    private fun checkPlayersLife(humanPlayer: PlayerViewModel, aiPlayers: Array<AIPlayerViewModel>) {
        if (humanPlayer.life.value!! < 0) {
            utils().alert(this.context, "Game Info", "You Lose")
            return
        }
        val aiPlayerList = aiPlayers.toCollection(ArrayList<AIPlayerViewModel>())
        for (i in 0..2) {
            if (aiPlayerList[i].life.value!! < 0) {
                aiPlayersViewDetailsButton[i].alpha = 0.3F
                aiPlayerList.removeAt(i)
            }
        }
        if (aiPlayerList.isEmpty()) {
            utils().alert(this.context, "Game Info", "You Win")
            return
        }
        this.gameBoardViewModel.aIPlayer.postValue(aiPlayerList.toTypedArray())
    }

    private fun checkKing(humanPlayer: PlayerViewModel, aiPlayers: Array<AIPlayerViewModel>) {
        for (textview in PlayersInfoTextView)
            textview.text = ""
        if (humanPlayer.isKing.value!!)
            PlayersInfoTextView[0].text = "👑"
        for (i in 0..2) {
            if (aiPlayers[i].isKing.value!!)
                PlayersInfoTextView[i + 1].text = "👑"
        }
    }

    private fun parsingDiceValue(diceValue: String):String {
        return diceValue.replace("{","")
            .replace("}","")
            .replace("ONE","1️⃣")
            .replace("TWO","2️⃣")
            .replace("THREE","3️⃣")
            .replace("ENERGY","⚡")
            .replace("LIFE","♥️")
            .replace("ATTACK","⚔️")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_board_page, container, false)
    }


    fun osef() {
        lifecycleScope.launch {
            playRound(1)
            displayPlayer()
            delay(2000)
            playRound(2)


        }
    }

    suspend fun playRound(player : Int) {
        withContext(Dispatchers.Default) {
            // IA plays
        }
    }
    suspend fun displayPlayer(){
        withContext(Dispatchers.Main) {
            // refresh display
            // textview.text = "sd"
        }
    }
}