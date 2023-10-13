package fr.epita.androidproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ToggleButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import fr.epita.androidproject.R
import fr.epita.androidproject.models.CardViewModel
import fr.epita.androidproject.models.GameBoardViewModel
import fr.epita.androidproject.models.PlayerViewModel
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
        val exitButton: Button = view.findViewById(R.id.store_exit_button)

        for (i in 0..2) {
            storeCardButtons[i].text =
                this.gameBoardViewModel.shopCards.value?.get(i)?.type.toString() + " + "+ this.gameBoardViewModel.shopCards.value?.get(i)?.effect.toString()
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
            buyCards(view, savedInstanceState)
        }

        resetButton.setOnClickListener() {
            resetCards(view, savedInstanceState)
        }

        exitButton.setOnClickListener() {
            findNavController().navigate(
                StorePageDirections.actionStorePageToGameBoardPage(null)
            )
        }
    }

    private fun buyCards(view: View, savedInstanceState: Bundle?) {
        val currPlayerEnergy = this.gameBoardViewModel.player.value?.energy?.value
        val numberOfCard = storeCardsSelected.count()

        var totalCost: Int = 0
        for (storeCardSelected in storeCardsSelected) {
            totalCost += storeCardSelected.price
        }

        var diff = currPlayerEnergy!! - totalCost
        if (diff < 0) {
            var diff = diff * -1
            utils().alert(this.context, "Shop Error", "You need $diff more energy")
        } else {
            utils().alertYesNo(
                this.context,
                "Shop Info",
                "Do you want to buy $numberOfCard card(s) for $totalCost ?",
                "Yes",
                "Too damn expensive",
                view,
                savedInstanceState,
                totalCost,
                ::buyCardsAction,
            )
        }
    }

    private fun buyCardsAction(view: View, savedInstanceState: Bundle?, totalCost: Int) {
        if (this.gameBoardViewModel.player.value?.immediateCards?.value?.size!! < 3) {
            for (storeCardSelected in storeCardsSelected) {
                this.gameBoardViewModel.player.value?.immediateCards?.value!!.add(
                    storeCardSelected
                )
                this.gameBoardViewModel.shopCards.value?.remove(storeCardSelected)
                this.gameBoardViewModel.shopCards.value?.add(CardViewModel())
            }
            var currEnergy = this.gameBoardViewModel.player.value?.energy?.value
            this.gameBoardViewModel.player.value?.energy?.postValue(currEnergy?.minus(totalCost))
            utils().alert(this.context, "Shop Info", "One or more card have been purchased")
            this.onViewCreated(view, savedInstanceState)
        } else {
            utils().alert(this.context, "Shop Error", "You cannot have more than 3 cards")
        }
    }

    private fun resetCards(view: View, savedInstanceState: Bundle?) {
        val currPlayerEnergy = this.gameBoardViewModel.player.value?.energy?.value

        if (currPlayerEnergy?.minus(3)!! <= 0) {
            utils().alert(this.context, "Shop Error", "Not enough energy in your wallet :(")
        } else {
            utils().alertYesNo(
                this.context,
                "Shop Info",
                "Resetting the shop will cost you 3 Energy",
                "Yes",
                "Too damn expensive !",
                view,
                savedInstanceState,
                3,
                ::resetCardsAction
            )
        }
    }

    private fun resetCardsAction(view: View, savedInstanceState: Bundle?, totalCost: Int) {
        var currEnergy = this.gameBoardViewModel.player.value?.energy?.value
        this.gameBoardViewModel.player.value?.energy?.postValue(currEnergy?.minus(totalCost))
        val shopCards: ArrayList<CardViewModel> = arrayListOf(
            CardViewModel(),
            CardViewModel(),
            CardViewModel()
        )
        this.gameBoardViewModel.shopCards.value = shopCards
        this.onViewCreated(view, savedInstanceState)
    }
}