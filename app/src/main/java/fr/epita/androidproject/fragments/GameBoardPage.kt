package fr.epita.androidproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import fr.epita.androidproject.R
import kotlin.system.exitProcess

class GameBoardPage : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val playButton : Button = view.findViewById(R.id.playButton)

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