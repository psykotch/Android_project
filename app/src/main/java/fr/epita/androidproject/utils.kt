package fr.epita.androidproject

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface


class utils {

    var dialogClickListener =
        DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {

                }

                DialogInterface.BUTTON_NEGATIVE -> {}
            }
        }

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
        messageNo: String
    ): Boolean {
        val builder = AlertDialog.Builder(context)
        var response: Boolean = true
        with(builder)
        {
            setTitle(title)
            setMessage(message)
            setNegativeButton(messageNo) { _, which ->
                when (which) {
                    DialogInterface.BUTTON_NEGATIVE -> {
                        response = false
                    }
                }
            }
            setPositiveButton(messageYes)
            { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        response = true
                    }

                }
                show()
            }
            return response
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