package fr.epita.androidproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import fr.epita.androidproject.R
import fr.epita.androidproject.models.DiceViewModel


class RollDicePage : Fragment() {

    private val dice: DiceViewModel by viewModels()
    private var dicesFaces: Array<DiceViewModel.diceFace> = arrayOf()
    private var dicesFacesSelected: ArrayList<String> = ArrayList();
    private var rollTimes: Int = 3

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val diceFaceButtons: ArrayList<ToggleButton> = ArrayList();
        diceFaceButtons.add(view.findViewById(R.id.dice_face_1))
        diceFaceButtons.add(view.findViewById(R.id.dice_face_2))
        diceFaceButtons.add(view.findViewById(R.id.dice_face_3))
        diceFaceButtons.add(view.findViewById(R.id.dice_face_4))
        diceFaceButtons.add(view.findViewById(R.id.dice_face_5))
        diceFaceButtons.add(view.findViewById(R.id.dice_face_6))

        fun rollAction() {
            dicesFaces = dice.roll()
            for (i in 0..5) {
                if (!diceFaceButtons[i].isChecked) {
                    diceFaceButtons[i].textOff = dicesFaces[i].toString()
                    diceFaceButtons[i].text = dicesFaces[i].toString()
                }
            }
            rollTimes -= 1
        }
        for (i in 0..5) {
            diceFaceButtons[i].setOnClickListener() {
                if (diceFaceButtons[i].isChecked) {
                    dicesFacesSelected.add(diceFaceButtons[i].textOff.toString())
                    diceFaceButtons[i].text = "Selected" + diceFaceButtons[i].textOff
                } else {
                    dicesFacesSelected.remove(diceFaceButtons[i].text.toString())
                }
            }
        }
        // 1st call
        rollAction()
        // call by click
        val rollButton: Button = view.findViewById(R.id.roll_dice_roll_button)
        rollButton.setOnClickListener() {
            rollAction()
            if (rollTimes == 0) {
                rollButton.isVisible = false
            }
        }
        val stopButton: Button = view.findViewById(R.id.roll_dice_stop_button)
        stopButton.setOnClickListener() {
            for (i in 0..5) {
                if (!diceFaceButtons[i].isChecked) {
                    dicesFacesSelected.add(diceFaceButtons[i].text.toString())
                }
            }

            findNavController().navigate(
                RollDicePageDirections.actionRollDicePageToGameBoardPage(
                    dicesFacesSelected.toTypedArray()
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_roll_dice, container, false)
    }
}