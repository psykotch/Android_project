package fr.epita.androidproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import fr.epita.androidproject.R
import fr.epita.androidproject.models.GameBroadViewModel
import kotlin.system.exitProcess

class MainPage : Fragment() {

    private val gameBroadViewModel: GameBroadViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val aboutButton : Button = view.findViewById(R.id.aboutButton)
        val selectPlayerButton : Button = view.findViewById(R.id.startButton)
        val selectQuitButton : Button = view.findViewById(R.id.quitButton)

        aboutButton.setOnClickListener() {
            findNavController().navigate(
                MainPageDirections.actionMainPageToAboutPage()
            )
        }

        selectPlayerButton.setOnClickListener() {
            findNavController().navigate(
                MainPageDirections.actionMainPageToSelectPlayerPage()
            )
        }

        selectQuitButton.setOnClickListener() {
            exitProcess(0);
        }

    }

}