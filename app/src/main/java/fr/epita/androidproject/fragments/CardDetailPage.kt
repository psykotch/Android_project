package fr.epita.androidproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fr.epita.androidproject.R
import fr.epita.androidproject.models.GameBoardViewModel
import fr.epita.androidproject.models.PlayerViewModel

class CardDetailPage : Fragment() {
    private val args: CardDetailPageArgs by navArgs()
    private val gameBroadViewModel: GameBoardViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card_page, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inforLabel: TextView = view.findViewById(R.id.card_page_view_info)
        val playerCards = this.gameBroadViewModel.player.value?.immediateCards?.value
        val player: PlayerViewModel
        if (args.cardID != null) {
            if (playerCards != null) {
                for (card in playerCards) {
                    if(card.uuid.toString() == args.cardID)
                        inforLabel.text  = "Effect : " + card.type + "+" + card.effect.toString() + "\n Price :" + card.price.toString()
                }
            }
        }

        val exitButton: Button = view.findViewById(R.id.player_details_exit_button)
        exitButton.setOnClickListener() {
            findNavController().navigate(
                PlayerDetailPageDirections.actionPlayerDetailPageToGameBoardPage(null)
            )
        }
    }
}