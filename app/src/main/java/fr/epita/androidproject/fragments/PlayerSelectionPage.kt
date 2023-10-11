package fr.epita.androidproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import fr.epita.androidproject.R
import fr.epita.androidproject.models.CardViewModel
import fr.epita.androidproject.models.GameBroadViewModel
import kotlin.system.exitProcess

class PlayerSelectionPage : Fragment() {

    //private val gameBroadViewModel: GameBroadViewModel by activityViewModels()
    private val gameBroadViewModel: GameBroadViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player_selection_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val playerName: EditText = view.findViewById(R.id.editTextText)
         playerName.editableText
        val startButton : Button = view.findViewById(R.id.button)


        startButton.setOnClickListener() {
            this.gameBroadViewModel.player.value!!.name.postValue(playerName.editableText.toString())
            findNavController().navigate(
                PlayerSelectionPageDirections.actionSelectPlayerPageToBoardPage(playerName.editableText.toString(),
                    null
                )
            )
        }


    }


}