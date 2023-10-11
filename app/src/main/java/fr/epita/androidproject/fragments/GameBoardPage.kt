package fr.epita.androidproject.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fr.epita.androidproject.R
import fr.epita.androidproject.models.AIPlayerViewModel
import fr.epita.androidproject.models.CardViewModel
import fr.epita.androidproject.models.GameBroadViewModel
import fr.epita.androidproject.models.PlayerViewModel
import kotlin.system.exitProcess

class GameBoardPage : Fragment() {
    val args: GameBoardPageArgs by navArgs()
    private val gameBroadViewModel: GameBroadViewModel by activityViewModels()

    //private val gameBroadViewModel: GameBroadViewModel by viewModels()
    private var playerName: String = "no name"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val preferencesEditor = sharedPreferences.edit()

        val playerName: TextView = view.findViewById(R.id.playerName)
        if (args.playerName != null) {
            this.playerName = args.playerName!!
            //this.gameBroadViewModel.player.value?.name?.postValue(this.playerName)
            preferencesEditor.putString("name", this.playerName)
            preferencesEditor.apply()
        } else
            this.playerName = sharedPreferences.getString("name", this.playerName)!!
            //this.playerName = this.gameBroadViewModel.player.value!!.name.value.toString()

        playerName.text = this.gameBroadViewModel.player.value!!.name.value.toString()

//        playerName.text = this.playerName
        val dicedResult: TextView = view.findViewById(R.id.playerName2)
        if (args.diceFaces != null) {
            dicedResult.text = parseDicesFaces(args.diceFaces!!).toString()
        }

        val playerDetailButton: Button = view.findViewById(R.id.viewDetailPlayer)
        playerDetailButton.setOnClickListener(){
            findNavController().navigate(
                GameBoardPageDirections.actionBoardPageToPlayerDetailPage(true)
            )
        }
        val playButton: Button = view.findViewById(R.id.playButton)
        playButton.setOnClickListener() {
            findNavController().navigate(
                GameBoardPageDirections.actionGameBoardPageToRollDicePage()
            )
        }
    }

    private fun parseDicesFaces(dicesFace: Array<String>): MutableMap<String, Int> {
        val frequencyMap: MutableMap<String, Int> = HashMap()

        for (face in dicesFace) {
            var count = frequencyMap[face]
            if (count == null) count = 0
            frequencyMap[face] = count + 1
        }
        return frequencyMap
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_game_board_page, container, false)
    }
}