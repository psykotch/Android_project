package fr.epita.androidproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import fr.epita.androidproject.R
import kotlin.system.exitProcess

class PlayerSelectionPage : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

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
        val StartButton : Button = view.findViewById(R.id.button)


        StartButton.setOnClickListener() {

            findNavController().navigate(
                PlayerSelectionPageDirections.actionSelectPlayerPageToBoardPage(playerName.editableText.toString(),null)
            )
        }


    }


}