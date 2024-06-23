package com.example.learnenglish.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.learnenglish.R

class FinishedActivity : AppCompatActivity() {
    private var totalNumberOfQuestion = 0
    private var numCorrectAnswer = 0
    private var point = 0
    private lateinit var email: String
    private lateinit var btnBack: Button
    private lateinit var tvNumCorrect: TextView
    private lateinit var tvPoint: TextView
    private lateinit var tvTotalNumQues: TextView
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finished)

        getDataFromInten()
        init()
        setUI()

    }
    private fun setUI(){
        btnBack.setOnClickListener {
            val intent = Intent(this@FinishedActivity, SkillActivity::class.java)
            intent.putExtra("email", email)
            startActivity(intent)
        }
    }
    private fun getDataFromInten(){
        val intent = intent
        totalNumberOfQuestion = intent.getIntExtra("totalNumberOfQuestion", 0)
        numCorrectAnswer = intent.getIntExtra("numCorrectAnswer", 0)
        email = intent.getStringExtra("email").toString()
        point = intent.getIntExtra("point", 0)
        Log.d("email end", email)
        Log.d("point end", point.toString())
    }
    @SuppressLint("SetTextI18n")
    private fun init(){
        btnBack = findViewById(R.id.btnchoilai)
        tvNumCorrect = findViewById(R.id.tv_numCorrect)
        tvTotalNumQues = findViewById(R.id.tv_totalNumCorrect)
        tvPoint = findViewById(R.id.tv_point)

        tvNumCorrect.text = numCorrectAnswer.toString()
        tvTotalNumQues.text = " / $totalNumberOfQuestion"
        tvPoint.text = point.toString()
    }
}