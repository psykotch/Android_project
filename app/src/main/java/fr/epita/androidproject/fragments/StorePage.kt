package fr.epita.androidproject.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ToggleButton
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import fr.epita.androidproject.R
import fr.epita.androidproject.models.CardViewModel
import fr.epita.androidproject.models.GameBoardViewModel
import fr.epita.androidproject.utils

class StorePage : Fragment() {

    private val gameBoardViewModel: GameBoardViewModel by activityViewModels()
    private var storeCardsSelected: ArrayList<CardViewModel> = ArrayList();

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
        return inflater.inflate(R.layout.fragment_store_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val storeCardButtons: ArrayList<ToggleButton> = ArrayList();
        storeCardButtons.add(view.findViewById(R.id.store_card_1))
        storeCardButtons.add(view.findViewById(R.id.store_card_2))
        storeCardButtons.add(view.findViewById(R.id.store_card_3))

        val resetButton: Button = view.findViewById(R.id.store_reset_button)
        val buyButton: Button = view.findViewById(R.id.store_buy_button)

        for (i in 0..2) {
            storeCardButtons[i].text =
                this.gameBoardViewModel.shopCards.value?.get(i)?.uuid.toString()
        }

        for (i in 0..2) {
            storeCardButtons[i].setOnClickListener() {
                if (storeCardButtons[i].isChecked) {
                    storeCardsSelected.add(this.gameBoardViewModel.shopCards.value!![i])
                    storeCardButtons[i].text = "Selected" + storeCardButtons[i].textOff
                } else {
                    storeCardsSelected.remove(this.gameBoardViewModel.shopCards.value!![i])
                }
            }
        }

        buyButton.setOnClickListener() {
            if (this.gameBoardViewModel.player.value?.immediateCards?.value?.size!! < 3) {
                for (storeCardSelected in storeCardsSelected) {
                    this.gameBoardViewModel.player.value?.immediateCards?.value!!.add(
                        storeCardSelected
                    )
                    this.gameBoardViewModel.shopCards.value?.remove(storeCardSelected)
                    this.gameBoardViewModel.shopCards.value?.add(CardViewModel())
                }

                findNavController().navigate(
                    StorePageDirections.actionStorePageToGameBoardPage(null)
                )
            } else {
                utils().alert(this.context,"Shop Error","You cannot have more than 3 cards")
            }
        }

        resetButton.setOnClickListener() {
            val shopCards: ArrayList<CardViewModel> = arrayListOf(
                CardViewModel(),
                CardViewModel(),
                CardViewModel()
            )
            this.gameBoardViewModel.shopCards.value = shopCards
            this.onViewCreated(view, savedInstanceState)
        }
    }
}