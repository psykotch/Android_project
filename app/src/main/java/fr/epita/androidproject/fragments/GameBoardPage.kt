package fr.epita.androidproject.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fr.epita.androidproject.R
import fr.epita.androidproject.models.GameBoardViewModel
import fr.epita.androidproject.utils

class GameBoardPage : Fragment() {
    val args: GameBoardPageArgs by navArgs()
    private val gameBoardViewModel: GameBoardViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val playerName: TextView = view.findViewById(R.id.playerName)
        playerName.text = this.gameBoardViewModel.player.value!!.name.value.toString()

        val cardButtons: ArrayList<Button> = ArrayList();
        cardButtons.add(view.findViewById(R.id.board_card_button_1))
        cardButtons.add(view.findViewById(R.id.board_card_button_2))
        cardButtons.add(view.findViewById(R.id.board_card_button_3))

        var count = 0
        for (card in this.gameBoardViewModel.player.value!!.immediateCards.value!!) {
            val cardButton = cardButtons[count]
            cardButton.text = card?.uuid.toString()
            count +=1
        }

        val dicedResult: TextView = view.findViewById(R.id.playerName2)
        if (args.diceFaces != null) {
            dicedResult.text = utils().parseDicesFaces(args.diceFaces!!).toString()
        }

        val playerDetailButton: Button = view.findViewById(R.id.viewDetailPlayer)
        playerDetailButton.setOnClickListener(){
            findNavController().navigate(
                GameBoardPageDirections.actionBoardPageToPlayerDetailPage(0,true)
            )
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_game_board_page, container, false)
    }
}