package com.example.learnenglish.activity

import android.annotation.SuppressLint
import android.view.animation.AnimationUtils
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.learnenglish.R
import com.example.learnenglish.contract.QuizzesContract
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.presenter.QuizzesPresenter
import com.example.learnenglish.repository.DBHelperRepository
import com.example.learnenglish_demo.QuizzAnswerModel
import com.example.learnenglish.model.QuizzQuestionModel
import com.example.learnenglish.model.UserModel

class QuizzesActivity : AppCompatActivity(), QuizzesContract.View , View.OnClickListener{
    private lateinit var presenter: QuizzesContract.Presenter
    private lateinit var dialog:AlertDialog
    private lateinit var tvContentQuestion: TextView
    private lateinit var imgBack: ImageView
    private lateinit var tvAnswer1: TextView
    private lateinit var tvAnswer2: TextView
    private lateinit var tvAnswer3: TextView
    private lateinit var tvAnswer4: TextView
    private lateinit var tvNumQuestion: TextView
    private lateinit var tvPoint: TextView
    private var mUser: UserModel? = null
    private var point: Int = 0
    lateinit var tvNumQuesCurent: TextView
    private lateinit var mListQues: ArrayList<QuizzQuestionModel>
    private lateinit var mListAns: ArrayList<QuizzAnswerModel>
    var id: String? = null
    private lateinit var email: String
    private var isAnswerSelected = false
    private var currentPos: Int = 0
    private var mediaPlayer:MediaPlayer? = null
    private lateinit var shakeAnimation: Animation
    private lateinit var zoomAnimation: Animation

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizzes)
        getDataFromIntent()
        initUi()
        setupPresenterAndFetchUser()
        setData(currentPos)
        btnQuit()
        tvNumQuestion.text = "/ " + mListQues.size.toString()
    }

    private fun getDataFromIntent(){
        val intent = intent
        id = intent.getStringExtra("topic").toString()
        email = intent.getStringExtra("email").toString()
    }

    private fun setData(pos: Int){
        if(pos >= 0 && pos < mListQues.size && pos < mListAns.size){
            tvContentQuestion.text = mListQues[currentPos].content
            tvAnswer1.text = mListAns[currentPos].answerA
            tvAnswer2.text = mListAns[currentPos].answerB
            tvAnswer3.text = mListAns[currentPos].answerC
            tvAnswer4.text = mListAns[currentPos].answerD
        }else{
            finish()
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setupPresenterAndFetchUser() {
        val taskRepository = DBHelperRepository(this)
        presenter = QuizzesPresenter(applicationContext, this@QuizzesActivity,taskRepository)
        presenter.getItemsQuestion()
        presenter.getItemsAnswer()
        presenter.getUser(email, object : TaskCallback.TaskCallbackUser2 {
            override fun onListUserLoaded(user: UserModel) {
                mUser = user
                point = user.point!!
                tvPoint.text = user.point.toString()
                Log.d("abssc", mUser!!.email.toString() + " " + mUser!!.point.toString())
            }
        })
    }

    private fun playSound() {
        mediaPlayer?.start()
    }
    override fun showQuestion(mListQuestion: ArrayList<QuizzQuestionModel>) {
        mListQues = mListQuestion
//        Glide.with(this)
//            .load(mListQuestion[currentPos].img)
//            .into(imgQuestion)
        tvContentQuestion.text = mListQues[currentPos].content

    }

    override fun showAnswer(mListAnswer: ArrayList<QuizzAnswerModel>) {
        mListAns = mListAnswer
        tvAnswer1.text = mListAnswer[currentPos].answerA
        tvAnswer2.text = mListAnswer[currentPos].answerB
        tvAnswer3.text = mListAnswer[currentPos].answerC
        tvAnswer4.text = mListAnswer[currentPos].answerD

        setOnClickListener()
    }

    override fun showResult(isCorrect: Boolean, textview: TextView) {
        if(isCorrect){
            mediaPlayer = MediaPlayer.create(this, R.raw.true_)
            playSound()
            textview.setBackgroundResource(R.drawable.bg_green_corner_30)
            textview.startAnimation(zoomAnimation)
        }else {
            textview.setBackgroundResource(R.drawable.bg_red_corner_10)
            textview.startAnimation(shakeAnimation)
            mediaPlayer = MediaPlayer.create(this, R.raw.false_)
            playSound()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun showNumQuesCurent(pos: Int) {
        tvNumQuesCurent.text = "$pos "
    }

    override fun showPoint(point: Int) {
        this.point = point
        tvPoint.text = point.toString()
    }

    override fun showNextQuestion(listQuestion: ArrayList<QuizzQuestionModel>, listAnswer: ArrayList<QuizzAnswerModel>, newCurrentPos: Int) {
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
        }, 1200)
    }

    override fun showQuizEndMessage() {
        Toast.makeText(this, "ket thuc", Toast.LENGTH_SHORT).show()
    }
    private fun btnQuit(){
        imgBack.setOnClickListener {
            val buid = AlertDialog.Builder(this,R.style.Themecustom)
            val view = layoutInflater.inflate(R.layout.customdialog_layout, null)
            buid.setView(view)
            buid.setCancelable(false)
            dialog = buid.create()
            dialog.setCancelable(false)
            dialog.show()
            view.findViewById<Button>(R.id.btn_no).setOnClickListener {
                dialog.dismiss()
            }
            view.findViewById<Button>(R.id.btn_yes).setOnClickListener {
                mUser?.id?.let { presenter.updatePoint(it, point) }
                finish()
            }
        }
    }
    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showActivityFinished(totalNumberOfQuestion: Int, numCorrectAnswer: Int, point: Int) {
        val intent = Intent(this, FinishedActivity::class.java)
        intent.putExtra("totalNumberOfQuestion", totalNumberOfQuestion)
        intent.putExtra("numCorrectAnswer", numCorrectAnswer)
        intent.putExtra("idUser", mUser?.id)
        intent.putExtra("point", point)
        intent.putExtra("email", email)
        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    private fun initUi() {
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake)
        zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom)

        mListQues = arrayListOf()
        tvContentQuestion = findViewById(R.id.txtcontent_question)
        imgBack = findViewById(R.id.img_back)
        tvNumQuestion = findViewById(R.id.tv_numQuestion)
        tvNumQuesCurent = findViewById(R.id.tv_numQuestionCurrent)
        tvAnswer1 = findViewById(R.id.txtanswer1)
        tvAnswer2 = findViewById(R.id.txtanswer2)
        tvAnswer3 = findViewById(R.id.txtanswer3)
        tvAnswer4 = findViewById(R.id.txtanswer4)
        tvPoint = findViewById(R.id.tv_point)

    }
    private fun setOnClickListener(){

        tvAnswer1.setBackgroundResource(R.drawable.bg_customitem)
        tvAnswer2.setBackgroundResource(R.drawable.bg_customitem)
        tvAnswer3.setBackgroundResource(R.drawable.bg_customitem)
        tvAnswer4.setBackgroundResource(R.drawable.bg_customitem)

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
                            point = presenter.checkAnswer(tvAnswer1, mListAns, mListQues, currentPos ++, point)
                        }, 1000)
                        isAnswerSelected = true // Đánh dấu đã chọn câu trả lời
                    }
                    R.id.txtanswer2 -> {
                        tvAnswer2.setBackgroundResource(R.drawable.orange_corner_30)
                        Handler().postDelayed({
                            point = presenter.checkAnswer(tvAnswer2, mListAns, mListQues,currentPos ++, point)
                        }, 1000)
                        isAnswerSelected = true
                    }
                    R.id.txtanswer3 -> {
                        tvAnswer3.setBackgroundResource(R.drawable.orange_corner_30)
                        Handler().postDelayed({
                            point = presenter.checkAnswer(tvAnswer3, mListAns, mListQues, currentPos ++, point)
                        }, 1000)
                        isAnswerSelected = true
                    }
                    R.id.txtanswer4 -> {
                        tvAnswer4.setBackgroundResource(R.drawable.orange_corner_30)
                        Handler().postDelayed({
                            point = presenter.checkAnswer(tvAnswer4, mListAns, mListQues, currentPos ++, point)
                        }, 1000)
                        isAnswerSelected = true
                    }
                }
            }
        }
    }

}