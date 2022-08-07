package com.example.tic_tac_toe_kt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class setup : AppCompatActivity() {

    // pre-declaring variables so that they can be used outside of the onCreate Function if needed
    private var btn_submit_names: Button? = null
    private var editTextPlayer1: EditText? = null
    private var editTextPlayer2: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        // creating ui objects to get the info
        btn_submit_names = findViewById(R.id.btn_submit_names)
        editTextPlayer1 = findViewById(R.id.editTextPlayer1)
        editTextPlayer2 = findViewById(R.id.editTextPlayer2)

        btn_submit_names!!.setOnClickListener {
            // checking if the players entered their names, if they didn't then send a toast
            if(editTextPlayer1!!.text.toString().isEmpty()){
                Toast.makeText(this, "Player 1 enter name", Toast.LENGTH_SHORT).show()
            }else if(editTextPlayer2!!.text.toString().isEmpty()){
                Toast.makeText(this, "Player 2 enter name", Toast.LENGTH_SHORT).show()
            }
            // if there are names then save them into the constants object and move to the game activity
            else{
                val intent = Intent(this, GameDisplay::class.java)
                intent.putExtra(Constants.Player_1_Name, editTextPlayer1!!.text.toString())
                intent.putExtra(Constants.Player_2_name, editTextPlayer2!!.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }
}