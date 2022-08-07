package com.example.tic_tac_toe_kt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private var play_button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        play_button = findViewById<Button>(R.id.play_button)

        // if play is clicked then open the setup activity
        play_button!!.setOnClickListener {
            val intent = Intent(this, setup::class.java)
            startActivity(intent)
            finish()
        }
    }

}