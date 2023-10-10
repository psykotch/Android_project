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
    private var dices_faces: Array<DiceViewModel.diceFace> = arrayOf()
    private var dices_faces_selected: ArrayList<String> = ArrayList();

    private var rollTimes: Int = 3

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text: TextView = view.findViewById(R.id.textView8)
        val diceFaceButtons: ArrayList<ToggleButton> = ArrayList();
        diceFaceButtons.add(view.findViewById(R.id.dice_face_1))
        diceFaceButtons.add(view.findViewById(R.id.dice_face_2))
        diceFaceButtons.add(view.findViewById(R.id.dice_face_3))
        diceFaceButtons.add(view.findViewById(R.id.dice_face_4))
        diceFaceButtons.add(view.findViewById(R.id.dice_face_5))
        diceFaceButtons.add(view.findViewById(R.id.dice_face_6))
        val rollButton: Button = view.findViewById(R.id.roll_dice_roll_button)
        val stopButton: Button = view.findViewById(R.id.roll_dice_stop_button)

        fun rollAction() {
            dices_faces = dice.roll()
            for (i in 0..5) {
                if (!diceFaceButtons[i].isChecked) {
                    print(dices_faces[i])
                    diceFaceButtons[i].textOff = dices_faces[i].toString()
                    diceFaceButtons[i].text = dices_faces[i].toString()
                }
            }
            rollTimes -= 1
        }
        for (i in 0..5) {
            diceFaceButtons[i].setOnClickListener() {
                if (diceFaceButtons[i].isChecked) {
                    dices_faces_selected.add(diceFaceButtons[i].textOff.toString())
                    text.text = "selected :" + dices_faces_selected.toString()
                } else {
                    dices_faces_selected.remove(diceFaceButtons[i].text.toString())
                    text.text = "selected :" + dices_faces_selected.toString()
                }
            }
        }
        // 1st call
        rollAction()
        // call by click
        rollButton.setOnClickListener() {
            rollAction()

            if (rollTimes == 0) {
                rollButton.isVisible = false
            }
        }
        stopButton.setOnClickListener() {
            for (i in 0..5) {
                if (!diceFaceButtons[i].isChecked) {
                    dices_faces_selected.add(diceFaceButtons[i].text.toString())
                }
            }
            val dices_faces_selected_array: Array<String> = dices_faces_selected.toTypedArray()
            findNavController().navigate(
                RollDicePageDirections.actionRollDicePageToGameBoardPage(
                    null,
                    dices_faces_selected_array
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