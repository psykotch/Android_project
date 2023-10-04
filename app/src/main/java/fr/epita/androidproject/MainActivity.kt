package fr.epita.androidproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.epita.androidproject.models.IA

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val truc = IA()

    }
}