package fr.epita.androidproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.ToggleButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fr.epita.androidproject.R
import fr.epita.androidproject.models.GameBoardViewModel
import fr.epita.androidproject.models.PlayerViewModel
import fr.epita.androidproject.utils

class GameBoardPage : Fragment() {
    val args: GameBoardPageArgs by navArgs()
    private val gameBoardViewModel: GameBoardViewModel by activityViewModels()

    val aiPlayersViewDetailsButton: ArrayList<ImageButton> = ArrayList();
    val aiPlayersVictory: ArrayList<TextView> = ArrayList()
    val aiPlayersLife: ArrayList<TextView> = ArrayList()
    val aiPlayersEnergy: ArrayList<TextView> = ArrayList()

    val humanPlayerStatView : HashMap<String,TextView> = HashMap()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val humanPlayer = this.gameBoardViewModel.player.value!!

        getPlayersStatView(view)
        updatePlayerStatView(humanPlayer)

        val playerName: TextView = view.findViewById(R.id.board_player1_name)
        playerName.text = this.gameBoardViewModel.player.value!!.name.value.toString()

        val cardButtons: ArrayList<Button> = ArrayList();
        cardButtons.add(view.findViewById(R.id.board_card_button_1))
        cardButtons.add(view.findViewById(R.id.board_card_button_2))
        cardButtons.add(view.findViewById(R.id.board_card_button_3))

        var count = 0
        for (card in this.gameBoardViewModel.player.value!!.immediateCards.value!!) {
            val cardButton = cardButtons[count]
            cardButton.text = card?.uuid.toString()
            count += 1
        }

        if (args.diceFaces != null) {
            val parsedResult = utils().parseDicesFaces(args.diceFaces!!)
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
        }

        val humanPlayerDetailButton: ImageButton = view.findViewById(R.id.board_player1_image_button)
        humanPlayerDetailButton.setOnClickListener() {
            findNavController().navigate(
                GameBoardPageDirections.actionBoardPageToPlayerDetailPage(0, true)
            )
        }

        val nextButton: Button = view.findViewById(R.id.nextButton)
        nextButton.setOnClickListener() {
            aiPlay(humanPlayer)
        }

        val playButton: Button = view.findViewById(R.id.playButton)
        playButton.setOnClickListener() {
            findNavController().navigate(
                GameBoardPageDirections.actionGameBoardPageToRollDicePage()
            )
        }

        val shopButton: Button = view.findViewById(R.id.shopButton)
        shopButton.setOnClickListener() {
            findNavController().navigate(
                GameBoardPageDirections.actionBoardPageToStorePage()
            )
        }
    }

    private fun getPlayersStatView(view: View) {
        aiPlayersViewDetailsButton.add(view.findViewById(R.id.board_player1_image_button))
        aiPlayersViewDetailsButton.add(view.findViewById(R.id.board_player2_image_button))
        aiPlayersViewDetailsButton.add(view.findViewById(R.id.board_player3_image_button))
        for (i in 0..2)
        {
            aiPlayersViewDetailsButton[i].setOnClickListener(){
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

        humanPlayerStatView["Life"]=view.findViewById(R.id.board_life_text)
        humanPlayerStatView["Victory"]=view.findViewById(R.id.board_victory_text)
        humanPlayerStatView["Energy"]=view.findViewById(R.id.board_energy_text)
    }

    private fun updatePlayerStatView(humanPlayer: PlayerViewModel) {
        val aiPlayers = gameBoardViewModel.aIPlayer.value
        for (i in 0..2) {
            aiPlayersVictory[i].text = aiPlayers?.get(i)?.score?.value.toString()
            aiPlayersLife[i].text = aiPlayers?.get(i)?.life?.value.toString()
            aiPlayersEnergy[i].text = aiPlayers?.get(i)?.energy?.value.toString()
        }
        humanPlayerStatView["Life"]?.text = humanPlayer.life?.value?.toString()
        humanPlayerStatView["Victory"]?.text = humanPlayer.score?.value?.toString()
        humanPlayerStatView["Energy"]?.text = humanPlayer.energy?.value?.toString()
    }

    private fun aiPlay(humanPlayer: PlayerViewModel) {

        for (aiPlayer in this.gameBoardViewModel.aIPlayer.value!!) {
            val aiDicedResults = aiPlayer.makeMove()
            val aiParsedResult = utils().parseDicesFaces(aiDicedResults)
            val aiAttack = aiParsedResult["ATTACK"]!!
            if (aiAttack > 0) {
                if (aiPlayer.isKing.value!!) {
                    //attack tout le monde
                    humanPlayer.life.value!!.minus(aiAttack)
                    for (aiPlayerLeft in this.gameBoardViewModel.aIPlayer.value!!) {
                        if (!aiPlayerLeft.isKing.value!! && aiPlayer != aiPlayerLeft)
                            aiPlayerLeft.life.postValue(
                                aiPlayerLeft.life.value!!.minus(aiAttack)
                            )
                    }
                } else {
                    if (humanPlayer.isKing.value!!) {
                        humanPlayer.life.value!!.minus(aiAttack)
                        if (!utils().alertYesNo(
                                this.context,
                                "Attacked",
                                "Do you want to get out of the tokyo",
                                "Yes",
                                "Hell no"
                            )
                        ) {
                            humanPlayer.isKing.postValue(false)
                            aiPlayer.isKing.postValue(true)
                        }
                    } else {
                        //attack the king
                        for (aiPlayerLeft in this.gameBoardViewModel.aIPlayer.value!!) {
                            if (aiPlayerLeft.isKing.value!! && aiPlayer != aiPlayerLeft) {
                                aiPlayerLeft.life.postValue(
                                    aiPlayerLeft.life.value!!.minus(aiAttack)
                                )
                                aiPlayerLeft.isKing.postValue(false)
                                aiPlayer.isKing.postValue(true)
                            }
                        }
                    }
                }
            }
        }

        updatePlayerStatView(humanPlayer)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_game_board_page, container, false)
    }
}