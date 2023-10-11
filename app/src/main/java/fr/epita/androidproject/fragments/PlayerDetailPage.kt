package fr.epita.androidproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fr.epita.androidproject.R
import fr.epita.androidproject.models.GameBroadViewModel
import fr.epita.androidproject.models.PlayerViewModel

class PlayerDetailPage : Fragment() {
    val args: PlayerDetailPageArgs by navArgs()
    private val gameBroadViewModel: GameBroadViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player_detail_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inforLabel: TextView = view.findViewById(R.id.player_details_infor_panel)
        val nameLabel: TextView = view.findViewById(R.id.player_details_name)

        val player: PlayerViewModel
        if (args.isPlayer) {
            player = this.gameBroadViewModel.player.value!!
            inforLabel.text =
                "Life : " + player.life.value.toString() + "\n Energy :" + player.energy.value.toString() + "\n Score:" + player.score.value.toString()
            nameLabel.text = player.name.value
        } else {
            player = this.gameBroadViewModel.aIPlayer.value!![args.aiPlayerIndex]
            inforLabel.text =
                "Life : " + player.life.value.toString() + "\n Energy :" + player.energy.value.toString() + "\n Score:" + player.score.value.toString()
            nameLabel.text = player.name.value
        }

        val exitButton: Button = view.findViewById(R.id.player_details_exit_button)
        exitButton.setOnClickListener() {
            findNavController().navigate(
                PlayerDetailPageDirections.actionPlayerDetailPageToGameBoardPage(null)
            )
        }


    }
}