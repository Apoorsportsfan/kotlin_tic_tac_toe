package com.example.tic_tac_toe_kt

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.math.ceil
import kotlin.math.min

class Board(
    context: Context,
    attr: AttributeSet

) : View(context, attr) {

    //attributes of the board
    private var boardColor: Int? = null
    private var XColor: Int? = null
    private var OColor: Int? = null
    private var winningLineColor: Int? = null

    // is there a winner?
    private var winner: Boolean = false

    // paint object
    private val paint: Paint = Paint()

    // temporary cell size
    private var cellSize: Int = width/3

    // game logic object
    private val game: GameLogic = GameLogic()

    // getting the colors from the xml file
    init {
        context.theme.obtainStyledAttributes(
            attr,
            R.styleable.Board,
            0,0).apply {
            try{
                boardColor = getInteger(R.styleable.Board_boardColor, 0)
                XColor = getInteger(R.styleable.Board_XColor, 0)
                OColor = getInteger(R.styleable.Board_OColor, 0)
                winningLineColor = getInteger(R.styleable.Board_winningLineColor, 0)
            } finally {
                recycle()
            }
        }
    }

    // Making the board a square and getting the cell size
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val dimension: Int = min(measuredHeight, measuredWidth)
        cellSize = dimension/3
        setMeasuredDimension(dimension, dimension)
    }


    override fun onDraw(canvas: Canvas?) {
        // setting paint properties
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true

        // drawing the game board
        paint.color = boardColor!!
        paint.strokeWidth = 16.toFloat()

        for(c in 1..2){
            canvas!!.drawLine(cellSize.toFloat() *c, 0.toFloat(), cellSize.toFloat() *c, width.toFloat(), paint)
        }

        for(r in 1..2){
            canvas!!.drawLine(0.toFloat(), cellSize.toFloat()* r, width.toFloat(), cellSize.toFloat() * r, paint)
        }

        // drawing the markers
        for(r in 0..2){
            for(c in 0..2){
                // checking if space is filled
                if(game.gameBoard[r][c] != 0){
                    // if it is a 1 draw an x
                    if(game.gameBoard[r][c] == 1){
                        paint.color = XColor!!

                        // bottom left to top right line
                        canvas!!.drawLine(
                            ((c + 1) * cellSize - cellSize*0.2).toFloat(),
                            (r * cellSize + cellSize * 0.2).toFloat(),
                            (c * cellSize + cellSize * 0.2).toFloat(),
                            ((r+1) * cellSize - cellSize*0.2).toFloat(),
                            paint
                        )

                        // top left to bottom right line
                        canvas.drawLine(
                            (c * cellSize + cellSize*0.2).toFloat(),
                            (r * cellSize + cellSize * 0.2).toFloat(),
                            ((c+1) * cellSize - cellSize * 0.2).toFloat(),
                            ((r+1) * cellSize - cellSize*0.2).toFloat(),
                            paint
                        )
                    }
                    // if it is not 1 than draw o
                    else{
                        paint.color = OColor!!

                        canvas!!.drawOval(
                            (c*cellSize + cellSize * 0.2).toFloat(),
                            (r *cellSize + cellSize * 0.2).toFloat(),
                            (c * cellSize + cellSize - cellSize *0.2).toFloat(),
                            (r*cellSize + cellSize - cellSize *0.2).toFloat(),
                            paint
                        )
                    }
                }
            }
        }

        // if there is a winner
        if(winner){
            paint.color = winningLineColor!!

            // getting row and collum
            val row = game.winType[0]
            val col = game.winType[1]

            // drawing winning line
            when(game.winType[2]){
                // first win type is horizontal line
                1 -> {
                    canvas!!.drawLine(
                        col.toFloat(),
                        (row*cellSize + cellSize * 0.5).toFloat(),
                        (cellSize * 3).toFloat(),
                        (row*cellSize + cellSize * 0.5).toFloat(),
                        paint
                    )
                }

                // second win type is vertical line
                2 -> {
                    canvas!!.drawLine(
                        (col*cellSize + cellSize * 0.5).toFloat(),
                        row.toFloat(),
                        (col*cellSize + cellSize * 0.5).toFloat(),
                        (cellSize * 3).toFloat(),
                        paint
                    )
                }

                // third is negative diagonal
                3 -> {
                    canvas!!.drawLine(
                        0.toFloat(),
                        0.toFloat(),
                        (cellSize*3).toFloat(),
                        (cellSize*3).toFloat(),
                        paint
                    )
                }

                // forth is positive diagonal
                4 -> {
                    canvas!!.drawLine(
                        0.toFloat(),
                        (cellSize*3).toFloat(),
                        (cellSize*3).toFloat(),
                        0.toFloat(),
                        paint
                    )
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x: Float = event!!.getX()
        val y: Float = event!!.getY()

        val action: Int = event!!.action

        if(action == MotionEvent.ACTION_DOWN){
            val row: Int = ceil(y/cellSize).toInt()
            val col: Int = ceil(x/cellSize).toInt()

            if(!winner){
                if(game.updateGameBoard(row, col)){
                    invalidate()

                    if(game.winnerCheck()){
                        winner = true
                    }

                    if (game.player == 2){
                        game.player = 1
                    }else{
                        game.player = 2
                    }

                }
            }
            return true
        }
        return false
    }

    // passing all views into the game logic class
    fun setUpGame(playAgain: Button, home: Button, playerDisplay: TextView, names: Array<String?>, linlay_record: LinearLayout){
        game.playAgainBTN = playAgain
        game.homeBtn = home
        game.playerTurn = playerDisplay
        game.playerNames = names
        game.linlay_record = linlay_record
        game.updateRecordDisplay()
    }

    // resetting the game logic
    fun resetGame(){
        game.resetGame()
        winner = false
    }



}