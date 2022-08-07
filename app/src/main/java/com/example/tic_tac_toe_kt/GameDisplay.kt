package com.example.tic_tac_toe_kt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.math.log

class GameDisplay : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_display)

        // setting play again button view to gone
        val playAgainBtn = findViewById<Button>(R.id.play_again_button)
        playAgainBtn.visibility = View.GONE

        // setting home button view to gone
        val homeBtn = findViewById<Button>(R.id.exit_button)
        homeBtn.visibility = View.GONE

        // setting first turn name
        val playerTurn: TextView = findViewById(R.id.playerTurn)
        val playerNames = arrayOf(intent.getStringExtra(Constants.Player_1_Name), intent.getStringExtra(Constants.Player_2_name))

        playerTurn.text = playerNames[0] +"'s Turn"

        // setting record visibility to gone
        val linlay_record: LinearLayout = findViewById<LinearLayout>(R.id.linlay_record)

        // setting up the game
        val board = findViewById<Board>(R.id.board)
        board.setUpGame(playAgainBtn, homeBtn, playerTurn, playerNames, linlay_record)

        // resetting game
        playAgainBtn.setOnClickListener {
            board.resetGame()
            board.invalidate()
        }

        // going back to main menu screen
        homeBtn.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}