package com.example.tic_tac_toe_kt

import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get

class GameLogic {

    // init gameBoard Array
    var gameBoard = arrayOf<Array<Int>>()

    // how did the person win?
    var winType = arrayOf(-1, -1, -1)

    // which player turn is it?
    var player: Int = 1

    // ui stuff
    var playerTurn: TextView? = null
    var playAgainBTN: Button? = null
    var homeBtn: Button? = null
    var playerNames: Array<String?>? = null

    // record display
    var linlay_record: LinearLayout? = null

    // record variables
    var player_1_wins: Int = 0
    var player_2_wins: Int = 0
    var ties: Int = 0


    init {
        for (r in 0..2) {
            var row = arrayOf<Int>()
            for (c in 0..2) {
                row += 0
            }
            gameBoard += row
        }
    }

    fun updateGameBoard(row: Int, col: Int): Boolean {
        // checking if space is empty
        if (gameBoard[row - 1][col - 1] == 0) {
            // if it is empty make the spot the player
            gameBoard[row - 1][col - 1] = player

            if (player == 1) {
                playerTurn!!.text = playerNames!![1] + "'s Turn"
            } else {
                playerTurn!!.text = playerNames!![0] + "'s Turn"
            }

            return true
        } else {
            return false
        }
    }

    fun winnerCheck(): Boolean {
        var isWinner = false

        // horizontal check
        for (r in 0..2) {
            if (gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][0] == gameBoard[r][2] && gameBoard[r][0] != 0) {
                winType = arrayOf(r, 0, 1)

                isWinner = true
            }
        }

        // vertical check
        for (c in 0..2) {
            if (gameBoard[0][c] == gameBoard[1][c] && gameBoard[0][c] == gameBoard[2][c] && gameBoard[0][c] != 0) {
                winType = arrayOf(0, c, 2)
                isWinner = true
            }
        }

        // neg diagonal check
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] && gameBoard[0][0] != 0) {
            winType = arrayOf(0, 2, 3)
            isWinner = true
        }

        // pos diagonal check
        if (gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2] && gameBoard[2][0] != 0) {
            winType = arrayOf(0, 2, 4)
            isWinner = true
        }

        // checking if the board is filled
        var boardFilled: Int = 0

        for (r in 0..2) {
            for (c in 0..2) {
                if (gameBoard[r][c] != 0) {
                    boardFilled += 1;
                }
            }
        }

        if (isWinner) {
            playAgainBTN!!.visibility = View.VISIBLE
            homeBtn!!.visibility = View.VISIBLE

            playerTurn!!.text = playerNames!![player - 1] + " WON!!!!"

            // updating record for the players
            if (player == 1) {
                player_1_wins++
            } else {
                player_2_wins++
            }

            updateRecordDisplay()
            return true;
        }
        // if board is filled declare tie
        else if (boardFilled == 9) {
            playAgainBTN!!.visibility = View.VISIBLE
            homeBtn!!.visibility = View.VISIBLE

            playerTurn!!.text = "TIE GAME!!!"

            // updating ties
            ties++

            updateRecordDisplay()
            return false
        } else {
            return false
        }
    }

    // updates the text for the record display above the board
    fun updateRecordDisplay() {
        // cycling through all of the views in the linear layout
        for (index in 0..3) {
            // getting the view
            val textView = linlay_record!!.getChildAt(index)
            // making sure the view is a textview (even though they are all textviews)
            if (textView is TextView) {
                when (index) {
                    // first item is the player 1 record
                    0 -> {
                        textView.text = playerNames!![0] + " " + player_1_wins.toString()
                    }
                    // second one is ties
                    1 -> {
                        textView.text = "Ties: " + ties.toString()
                    }
                    // third is player 2 record
                    2 -> {
                        textView.text = playerNames!![1] + " " + player_2_wins.toString()
                    }
                    // last is total games played
                    3 -> {
                        textView.text =
                            "Total Games: " + (ties + player_1_wins + player_2_wins).toString()
                    }
                }
            }
        }
    }

    // resets the game to be an empty board
    fun resetGame() {
        for (r in 0..2) {
            for (c in 0..2) {
                gameBoard[r][c] = 0
            }
        }

        player = 1

        playAgainBTN!!.visibility = View.GONE
        homeBtn!!.visibility = View.GONE

        playerTurn!!.text = playerNames!![0] + "'s Turn"
    }

}