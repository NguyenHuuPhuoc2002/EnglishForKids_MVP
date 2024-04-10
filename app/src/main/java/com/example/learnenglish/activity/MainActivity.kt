package com.example.learnenglish.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.learnenglish.R
import com.example.learnenglish.contract.TaskContract
import com.example.learnenglish.presenter.TaskPresenter
import com.example.learnenglish.repository.DBHelperRepository
import com.example.learnenglish_demo.AnswerModel
import com.example.learnenglish_demo.QuestionModel

class MainActivity : AppCompatActivity(), TaskContract.View , View.OnClickListener{
    private lateinit var presenter: TaskContract.Presenter
    private lateinit var tvContentQuestion: TextView
    private lateinit var imgQuestion: ImageView
    private lateinit var tvAnswer1: TextView
    private lateinit var tvAnswer2: TextView
    private lateinit var tvAnswer3: TextView
    private lateinit var tvAnswer4: TextView
    private lateinit var mListQues: ArrayList<QuestionModel>
    private lateinit var mListAns: ArrayList<AnswerModel>
    var id: String? = null
    private var isAnswerSelected = false
    private var currentPos: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = intent
        id = intent.getStringExtra("topic").toString()
        mListQues = arrayListOf()
        initUi()
        val taskRepository = DBHelperRepository(this)
        presenter = TaskPresenter(applicationContext, this@MainActivity,taskRepository)
        presenter.getItemsQuestion()
        presenter.getItemsAnswer()

    }


    override fun showQuestion(mListQuestion: ArrayList<QuestionModel>) {
        mListQues = mListQuestion
//        Glide.with(this)
//            .load(mListQuestion[currentPos].img)
//            .into(imgQuestion)
        tvContentQuestion.text = mListQues[currentPos].content
        setOnClickListener()
    }

    override fun showAnswer(mListAnswer: ArrayList<AnswerModel>) {
        mListAns = mListAnswer

        tvAnswer1.text = mListAnswer[currentPos].answerA
        tvAnswer2.text = mListAnswer[currentPos].answerB
        tvAnswer3.text = mListAnswer[currentPos].answerC
        tvAnswer4.text = mListAnswer[currentPos].answerD

        setOnClickListener()
    }

    override fun showResult(isCorrect: Boolean, textview: TextView) {
        if(isCorrect){
            textview.setBackgroundResource(R.drawable.bg_green_corner_30)
        }else {
            textview.setBackgroundResource(R.drawable.bg_red_corner_10)
        }
    }

    override fun showNextQuestion(listQuestion: ArrayList<QuestionModel>, listAnswer: ArrayList<AnswerModel>, newCurrentPos: Int) {
        tvAnswer1.isEnabled = true
        tvAnswer2.isEnabled = true
        tvAnswer3.isEnabled = true
        tvAnswer4.isEnabled = true
        Handler().postDelayed({
            setOnClickListener()

            tvContentQuestion.text = listQuestion[newCurrentPos].content
//            Glide.with(this)
//                .load(listQuestion[newCurrentPos].img)
//                .into(imgQuestion)
            tvAnswer1.text = listAnswer[newCurrentPos].answerA
            tvAnswer2.text = listAnswer[newCurrentPos].answerB
            tvAnswer3.text = listAnswer[newCurrentPos].answerC
            tvAnswer4.text = listAnswer[newCurrentPos].answerD

            isAnswerSelected = false
        }, 1500)
    }


    override fun showQuizEndMessage() {
        Toast.makeText(this, "ket thuc", Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun initUi() {
        tvContentQuestion = findViewById(R.id.txtcontent_question)
       // imgQuestion = findViewById(R.id.img_question)
        tvAnswer1 = findViewById(R.id.txtanswer1)
        tvAnswer2 = findViewById(R.id.txtanswer2)
        tvAnswer3 = findViewById(R.id.txtanswer3)
        tvAnswer4 = findViewById(R.id.txtanswer4)

    }
    private fun setOnClickListener(){

        tvAnswer1.setBackgroundResource(R.drawable.pink_boder_answer)
        tvAnswer2.setBackgroundResource(R.drawable.pink_boder_answer)
        tvAnswer3.setBackgroundResource(R.drawable.pink_boder_answer)
        tvAnswer4.setBackgroundResource(R.drawable.pink_boder_answer)

        tvAnswer1.setOnClickListener(this)
        tvAnswer2.setOnClickListener(this)
        tvAnswer3.setOnClickListener(this)
        tvAnswer4.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        if (!isAnswerSelected) {
            if (v != null) {
                when (v.id) {
                    R.id.txtanswer1 -> {
                        tvAnswer1.setBackgroundResource(R.drawable.orange_corner_30)
                        Handler().postDelayed({
                            presenter.checkAnswer(tvAnswer1, mListAns, mListQues, currentPos ++)
                        }, 1200)
                        isAnswerSelected = true // Đánh dấu đã chọn câu trả lời
                    }
                    R.id.txtanswer2 -> {
                        tvAnswer2.setBackgroundResource(R.drawable.orange_corner_30)
                        Handler().postDelayed({
                            presenter.checkAnswer(tvAnswer2, mListAns, mListQues,currentPos ++)
                        }, 1200)
                        isAnswerSelected = true
                    }
                    R.id.txtanswer3 -> {
                        tvAnswer3.setBackgroundResource(R.drawable.orange_corner_30)
                        Handler().postDelayed({
                            presenter.checkAnswer(tvAnswer3, mListAns, mListQues, currentPos ++)
                        }, 1200)
                        isAnswerSelected = true
                    }
                    R.id.txtanswer4 -> {
                        tvAnswer4.setBackgroundResource(R.drawable.orange_corner_30)
                        Handler().postDelayed({
                            presenter.checkAnswer(tvAnswer4, mListAns, mListQues, currentPos ++)
                        }, 1200)
                        isAnswerSelected = true
                    }
                }
            }
        }
    }

}