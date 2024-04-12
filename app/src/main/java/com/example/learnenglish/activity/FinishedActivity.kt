package com.example.learnenglish.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.learnenglish.R

class FinishedActivity : AppCompatActivity() {
    private var totalNumberOfQuestion = 0
    private var numCorrectAnswer = 0
    private lateinit var btnBack: Button
    private lateinit var tvNumCorrect: TextView
    private lateinit var tvTotalNumQues: TextView
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finished)
        val intent = intent
        totalNumberOfQuestion = intent.getIntExtra("totalNumberOfQuestion", 0)
        numCorrectAnswer = intent.getIntExtra("numCorrectAnswer", 0)

        init()

        tvNumCorrect.text = numCorrectAnswer.toString()
        tvTotalNumQues.text = " / $totalNumberOfQuestion"
        btnBack.setOnClickListener {
            startActivity(Intent(this@FinishedActivity, TopicActivity::class.java))
        }
    }
    private fun init(){
        btnBack = findViewById(R.id.btnchoilai)
        tvNumCorrect = findViewById(R.id.tv_numCorrect)
        tvTotalNumQues = findViewById(R.id.tv_totalNumCorrect)
    }
}