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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fr.epita.androidproject.R
import fr.epita.androidproject.models.AIPlayerViewModel
import fr.epita.androidproject.models.GameBoardViewModel
import fr.epita.androidproject.models.PlayerViewModel
import fr.epita.androidproject.utils

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
    private val playerCardsViewButton: ArrayList<ImageButton> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val humanPlayer = this.gameBoardViewModel.player.value!!
        val aiPlayers = this.gameBoardViewModel.aIPlayer.value!!
        val nextButton: Button = view.findViewById(R.id.nextButton)
        val playButton: Button = view.findViewById(R.id.playButton)
        val shopButton: Button = view.findViewById(R.id.shopButton)
        val playerName: TextView = view.findViewById(R.id.board_player1_name)

        getPlayersStatView(view)
        updatePlayerStatView(humanPlayer)

        getPlayerCardView(view)

        if (!this.gameBoardViewModel.isStarted.value!!) {
            this.gameBoardViewModel.isStarted.postValue(true)
            humanPlayer.isKing.postValue(true)
            PlayersInfoTextView[0].text = "👑"
        }
        playerName.text = humanPlayer.name.value.toString()

        val cardButtons: ArrayList<Button> = ArrayList();
        cardButtons.add(view.findViewById(R.id.board_card_button_1))
        cardButtons.add(view.findViewById(R.id.board_card_button_2))
        cardButtons.add(view.findViewById(R.id.board_card_button_3))

        var count = 0
        for (card in humanPlayer.immediateCards.value!!) {
            val cardButton = cardButtons[count]
            cardButton.text = card.uuid.toString()
            count += 1
        }

        if (args.diceFaces != null) {
            val parsedResult = utils().parseDicesFaces(args.diceFaces!!)
            //humanPlayer.lastDicedValues.postValue(parsingDiceValue(parsedResult.toString()))
            PlayersDiceInfoTextView[0].text = parsingDiceValue(parsedResult.toString())
            val attack = parsedResult["ATTACK"]!!
            if (attack > 0) {
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
            nextButton.isVisible = true
        }

        val humanPlayerDetailButton: ImageButton = view.findViewById(R.id.board_player1_image_button)
        humanPlayerDetailButton.setOnClickListener() {
            findNavController().navigate(
                GameBoardPageDirections.actionBoardPageToPlayerDetailPage(0, true)
            )
        }

        nextButton.setOnClickListener() {
            playButton.isVisible = false
            // utils().alert(this.context, "Game Info", "You have Ended your turn, please wait for these others")
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

    private fun getPlayerCardView(view: View) {
        playerCardsViewButton.add(view.findViewById(R.id.board_card_button_1))
        playerCardsViewButton.add(view.findViewById(R.id.board_card_button_2))
        playerCardsViewButton.add(view.findViewById(R.id.board_card_button_3))
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

    private fun updatePlayerStatView(humanPlayer: PlayerViewModel) {
        val aiPlayers = gameBoardViewModel.aIPlayer.value
        for (i in 0..2) {
            aiPlayersVictory[i].text = aiPlayers?.get(i)?.score?.value.toString()
            aiPlayersLife[i].text = aiPlayers?.get(i)?.life?.value.toString()
            aiPlayersEnergy[i].text = aiPlayers?.get(i)?.energy?.value.toString()
        }
        humanPlayerStatView["Life"]?.text = humanPlayer.life.value?.toString()
        humanPlayerStatView["Victory"]?.text = humanPlayer.score.value?.toString()
        humanPlayerStatView["Energy"]?.text = humanPlayer.energy.value?.toString()
    }

    private fun aiPlay(humanPlayer: PlayerViewModel, aiPlayers: Array<AIPlayerViewModel>) {

        for (i in 0..2) {
            val aiPlayer = aiPlayers[i]
            val aiDicedResults = aiPlayer.makeMove()
            this.PlayersInfoTextView[i + 1].text = "PLAYING..."
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
            this.PlayersInfoTextView[i + 1].text = ""
        }
        updatePlayerStatView(humanPlayer)
        checkPlayersLife(humanPlayer, aiPlayers)
        checkKing(humanPlayer, aiPlayers)
    }

    private fun tokyoGetOut(
        humanPlayer: PlayerViewModel,
        aiPlayer: AIPlayerViewModel
    ) {
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
}