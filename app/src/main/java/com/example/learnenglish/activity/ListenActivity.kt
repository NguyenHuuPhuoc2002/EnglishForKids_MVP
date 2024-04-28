package com.example.learnenglish.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.learnenglish.R
import com.example.learnenglish.contract.ListenContract
import com.example.learnenglish.model.ListenAnswerModel
import com.example.learnenglish.model.ListenQuestionModel
import com.example.learnenglish.presenter.ListenPresenter
import com.example.learnenglish.repository.DBHelperRepository
import java.io.File

class ListenActivity : AppCompatActivity(), ListenContract.View {
    companion object {
        val handler = Handler(Looper.getMainLooper())
    }
    private lateinit var presenter: ListenPresenter
    private lateinit var btnLoaQues: ImageView
    private lateinit var btnLoaAns: ImageView
    private lateinit var btnCheck: Button
    private lateinit var btnQuit: Button
    private lateinit var btnNext: Button
    private lateinit var tvQuestion: TextView
    private lateinit var tvAnswer: TextView
    private lateinit var edtAnswer: EditText
    private lateinit var mListQues: ArrayList<ListenQuestionModel>
    private lateinit var mListAns: ArrayList<ListenAnswerModel>
    var id: String? = null
    private var createPos: Int = 0
    private var currentPos: Int = 0
    private var mediaPlayer:MediaPlayer? = null
    private lateinit var shakeAnimation: Animation
    private lateinit var zoomAnimation: Animation
    private lateinit var zoomImgAnimation: Animation
    private lateinit var zoomCharacterAnimation: Animation
    private lateinit var dialog:AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listen)
        val intent = intent
        id = intent.getStringExtra("topic").toString()
        Log.d("id", id.toString())
        init()
        getData()
        setData(createPos)
        btnCheck()
        btnLoaQues()
        btnLoaAns()
        btnQuit()
    }
    private fun btnLoaQues(){
        btnLoaQues.setOnClickListener {
            mediaPlayer!!.stop()
            freeUpResources()
            val audioBytes = mListQues[currentPos].audio
            mediaPlayer = byteArrayToAudio(this@ListenActivity, audioBytes)
            playSound()
        }
    }
    private fun btnLoaAns(){
        btnLoaAns.setOnClickListener {
            mediaPlayer!!.stop()
            freeUpResources()
            val audioBytes = mListAns[currentPos].audio
            mediaPlayer = byteArrayToAudio(this@ListenActivity, audioBytes)
            playSound()
        }
    }
    private fun btnCheck() {
        btnCheck.setOnClickListener {
            Log.d("title", mListAns[currentPos].isCorrect)
            if (edtAnswer.text.isEmpty()) {
                edtAnswer.startAnimation(shakeAnimation)
            } else {
                presenter.checkAnswer(edtAnswer, mListQues, mListAns, createPos++)
            }
        }
    }
    private fun byteArrayToAudio(context: Context?, audioBytes: ByteArray?): MediaPlayer? {
        val tempFile = File.createTempFile("temp_audio", null, context?.cacheDir)
        tempFile.deleteOnExit()
        if (audioBytes != null) {
            tempFile.writeBytes(audioBytes)
        }
        return MediaPlayer.create(context, Uri.fromFile(tempFile))
    }


    private fun playSound() {
        mediaPlayer?.start()
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
    private fun getData(){
        val listenRepository = DBHelperRepository(this)
        presenter = ListenPresenter(applicationContext, this@ListenActivity,listenRepository)
        presenter.getItemsListenQuestion()
        presenter.getItemsListenAnswer()

    }

    @SuppressLint("MissingInflatedId")
    private fun btnQuit() {
        btnQuit.setOnClickListener {
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
                finish()
            }
        }
    }
    private fun btnNext(pos: Int) {
        btnNext.setOnClickListener {
            presenter.nextQuestion(mListQues, mListAns, pos)
            btnNext.visibility = View.GONE
            btnCheck.visibility = View.VISIBLE
        }
    }
    @SuppressLint("CutPasteId")
    private fun init() {
        mListQues = arrayListOf()
        mListAns = arrayListOf()
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake)
        zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom)
        zoomImgAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_img)
        zoomCharacterAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_character)

        btnLoaQues = findViewById(R.id.img_loa_question)
        btnNext = findViewById(R.id.btn_next)
        btnCheck = findViewById(R.id.btn_check)
        btnQuit = findViewById(R.id.btn_quit)
        btnLoaAns = findViewById(R.id.img_loa_answer)
        tvQuestion = findViewById(R.id.tv_question_listen)
        tvAnswer = findViewById(R.id.tv_answer_listen)
        edtAnswer = findViewById(R.id.edt_answer)
    }

    override fun showListenQuestion(mListQuestion: ArrayList<ListenQuestionModel>) {
        mListQues = mListQuestion
        tvQuestion.text = mListQues[createPos].content
        val audioBytes = mListQues[createPos].audio
        mediaPlayer = byteArrayToAudio(this@ListenActivity, audioBytes)
        playSound()
    }

    override fun showListenAnswer(mListAnswer: ArrayList<ListenAnswerModel>) {
        mListAns = mListAnswer
    }


    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showActivityFinished(totalNumberOfQuestion: Int, numCorrectAnswer: Int, point: Int) {
        val intent = Intent(this, FinishedActivity::class.java)
        intent.putExtra("totalNumberOfQuestion", totalNumberOfQuestion)
        intent.putExtra("numCorrectAnswer", numCorrectAnswer)
        startActivity(intent)
    }

    override fun showResult(isCorrect: Boolean, textview: TextView) {
        if(isCorrect){
            VocabularyActivity.handler.postDelayed({
//                textview.setBackgroundResource(R.drawable.bg_green_corner_listen)
                btnNext.setBackgroundResource(R.drawable.bg_green_corner_30)
                tvAnswer.text = mListAns[currentPos].isCorrect
                btnCheck.visibility = View.GONE
                btnNext.visibility = View.VISIBLE
                mediaPlayer = MediaPlayer.create(this, R.raw.true_)
                playSound()
                btnNext(currentPos)
                btnNext.startAnimation(zoomAnimation)
            }, 200)
        }else {
            mediaPlayer!!.stop()
            freeUpResources()
            mediaPlayer = MediaPlayer.create(this, R.raw.false_)
            playSound()
            handler.postDelayed({
               // textview.setBackgroundResource(R.drawable.bg_red_corner_listen)
                btnCheck.setBackgroundResource(R.drawable.bg_red_corner_10)
                btnCheck.startAnimation(shakeAnimation)
            }, 200)
        }
    }

    override fun showNextQuestion(listQuestion: ArrayList<ListenQuestionModel>, listAnswer: ArrayList<ListenAnswerModel>,
                                  newCurrentPos: Int
    ) {
        currentPos = newCurrentPos
        setData(newCurrentPos)
    }
    @SuppressLint("SetTextI18n")
    override fun setData(pos: Int){
        if(pos >= 0 && pos < mListQues.size){
            tvQuestion.text = mListQues[pos].content
            val audioBytesQues = mListQues[pos].audio
            val audioBytesAns = mListAns[pos].audio
            mediaPlayer = byteArrayToAudio(this@ListenActivity, audioBytesQues)
            playSound()
            handler.postDelayed({
                mediaPlayer = byteArrayToAudio(this@ListenActivity, audioBytesAns)
                playSound()
            }, 4000)
            edtAnswer.setBackgroundResource(R.drawable.pink_boder_white)
            tvAnswer.text = "_________________"
            edtAnswer.text.clear()
        }else{
            finish()
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show()
        }
    }
    private fun freeUpResources(){
        mediaPlayer!!.release()
        mediaPlayer = null
    }
}