package fr.epita.androidproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fr.epita.androidproject.R
import kotlin.system.exitProcess

class GameBoardPage : Fragment() {
    val args: GameBoardPageArgs by navArgs()
    private var playerName:String = "no name"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val playButton : Button = view.findViewById(R.id.playButton)

        val playerName : TextView = view.findViewById(R.id.playerName)
        val playerName2 : TextView = view.findViewById(R.id.playerName2)
        if(args.playerName != null)
        {
            this.playerName = args.playerName!!
            playerName.text = this.playerName
        }
        // if (args.diceFaces != null)
        print(args.diceFaces)
            playerName2.text = args.diceFaces.toString()

        playButton.setOnClickListener() {
            findNavController().navigate(
                GameBoardPageDirections.actionGameBoardPageToRollDicePage()
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