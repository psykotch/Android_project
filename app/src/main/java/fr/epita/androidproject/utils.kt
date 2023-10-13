package fr.epita.androidproject

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import fr.epita.androidproject.models.AIPlayerViewModel
import fr.epita.androidproject.models.GameBoardViewModel
import fr.epita.androidproject.models.PlayerViewModel


class utils {
    fun alert(context: Context?, title: String, message: String) {
        val builder = AlertDialog.Builder(context)

        with(builder)
        {
            setTitle(title)
            setMessage(message)
            setCancelable(true)
            show()
        }
    }
    fun alertYesNo(
        context: Context?,
        title: String,
        message: String,
        messageYes: String,
        messageNo: String,
        humanPlayer: PlayerViewModel,
        aiPlayer: AIPlayerViewModel,
        triggerOnYes: (humanPlayer: PlayerViewModel, aiPlayer: AIPlayerViewModel) -> Unit
    ) {
        val builder = AlertDialog.Builder(context)
        with(builder)
        {
            setTitle(title)
            setMessage(message)
            setNegativeButton(messageNo) { _, which ->
                when (which) {
                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }
            setPositiveButton(messageYes)
            { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        triggerOnYes(humanPlayer, aiPlayer)
                    }
                }
            }
            show()
        }
    }

    fun alertYesNo(
        context: Context?,
        title: String,
        message: String,
        messageYes: String,
        messageNo: String,
        view: View,
        savedInstanceState: Bundle?,
        totalCost: Int,
        triggerOnYes: (view: View, savedInstanceState: Bundle?, totalCost: Int) -> Unit
    ) {
        val builder = AlertDialog.Builder(context)
        with(builder)
        {
            setTitle(title)
            setMessage(message)
            setNegativeButton(messageNo) { _, which ->
                when (which) {
                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }
            setPositiveButton(messageYes)
            { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        triggerOnYes(view, savedInstanceState, totalCost)
                    }
                }
            }
            show()
        }
    }

    fun parseDicesFaces(dicesFace: Array<String>): MutableMap<String, Int> {
        val frequencyMap: MutableMap<String, Int> = HashMap()

        for (face in dicesFace) {
            var count = frequencyMap[face]
            if (count == null) count = 0
            frequencyMap[face] = count + 1
        }
        return frequencyMap
    }
}