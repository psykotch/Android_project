package fr.epita.androidproject

import android.app.AlertDialog
import android.content.Context

class utils {

    fun alert (context: Context?, title: String, message: String) {
        val builder = AlertDialog.Builder(context)

        with(builder)
        {
            setTitle(title)
            setMessage(message)
            setCancelable(true)
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