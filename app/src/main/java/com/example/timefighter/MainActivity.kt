package com.example.timefighter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    var tvCount : TextView ?= null
    var tvTimeLeft : TextView ?= null
    var btnTapMe : Button ?= null

    var score = 0

    var gameStarted = false

    lateinit var countDownTimer: CountDownTimer
    var initialCountDown: Long = 60000
    var countDownInterval: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        onClickListener()

    }

    private fun onClickListener() {

        btnTapMe?.setOnClickListener {view ->
            val bounceAnimation = AnimationUtils.loadAnimation(this,R.anim.bounce)
            view.startAnimation(bounceAnimation)
            incrementScore()

        }

        resetGame()

    }

    private fun resetGame() {
        score = 0
        tvCount?.text = score.toString()

        val initialTimeLeft = initialCountDown / 1000
        tvTimeLeft?.text = initialTimeLeft.toString()

        countDownTimer = object : CountDownTimer (initialCountDown, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished / 1000
                tvTimeLeft?.text = timeLeft.toString()

            }

            override fun onFinish() {
                endGame()
            }


        }

        gameStarted = false

    }

    private fun incrementScore() {
        if(!gameStarted){
            startGame()
        }

        score += 1
        tvCount?.text = score.toString()

    }

    private fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    private fun endGame() {
        Toast.makeText(this, getString(R.string.gameOverMessage,score), Toast.LENGTH_LONG).show()
        resetGame()
    }

    private fun init(){

        tvCount = findViewById(R.id.tvCount)
        tvTimeLeft = findViewById(R.id.tvTimeLeft)
        btnTapMe = findViewById(R.id.btnTapHere)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.actionAbout){
            showInfo()
        }
        return true
    }

    private fun showInfo() {
        val dialogTitle = getString(R.string.aboutTitle)
        val dialogMessage = getString(R.string.aboutMessage)

        val builder = AlertDialog.Builder(this)
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.create().show()
    }

}