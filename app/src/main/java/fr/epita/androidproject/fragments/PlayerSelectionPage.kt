package fr.epita.androidproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

        val player1 : ImageButton = view.findViewById(R.id.PlayerSelect_1)
        val player2 : ImageButton = view.findViewById(R.id.PlayerSelect_3)
        val player3 : ImageButton = view.findViewById(R.id.PlayerSelect_2)
        val player4 : ImageButton = view.findViewById(R.id.PlayerSelect_4)


        player1.setOnClickListener() {
            changePage()
        }
        player2.setOnClickListener() {
            changePage()
        }
        player3.setOnClickListener() {
            changePage()
        }
        player4.setOnClickListener() {
            changePage()
        }

    }

    fun  changePage(){
        findNavController().navigate(
            PlayerSelectionPageDirections.actionSelectPlayerPageToBoardPage()
        )
    }
}