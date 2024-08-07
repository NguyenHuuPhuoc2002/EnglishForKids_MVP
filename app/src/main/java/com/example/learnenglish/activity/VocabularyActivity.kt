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
import android.view.animation.AnimationUtils
import android.view.View
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.learnenglish.R
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.contract.VocabularyContract
import com.example.learnenglish.model.UserModel
import com.example.learnenglish.model.VocabularyAnsModel
import com.example.learnenglish.model.VocabularyQuesModel
import com.example.learnenglish.presenter.VocabularyPresenter
import com.example.learnenglish.repository.DBHelperRepository
import java.io.File

class VocabularyActivity : AppCompatActivity(), View.OnClickListener, VocabularyContract.View {
    companion object {
        val handler = Handler(Looper.getMainLooper())
    }
    private lateinit var tvCharacter1: TextView
    private lateinit var tvCharacter2: TextView
    private lateinit var tvCharacter3: TextView
    private lateinit var tvCharacter4: TextView
    private lateinit var tvCharacter5: TextView
    private lateinit var tvCharacter6: TextView
    private lateinit var tvCharacter7: TextView
    private lateinit var tvCharacter8: TextView
    private lateinit var tvCharacter9: TextView
    private lateinit var tvCharacter10: TextView
    private lateinit var tvCharacter11: TextView
    private lateinit var tvCharacter12: TextView
    private lateinit var tvNumQuestion: TextView
    private lateinit var tvPoint: TextView
    private lateinit var btnQuit: Button
    private lateinit var btnRestart: ImageView
    private lateinit var btnSpeaker: ImageView
    lateinit var tvNumQuesCurent: TextView
    private lateinit var imgOb: ImageView
    private lateinit var tvQuestion: TextView
    private lateinit var btnCheck: Button
    private lateinit var tvShow: TextView
    private lateinit var mListQues: ArrayList<VocabularyQuesModel>
    private lateinit var mListAns: ArrayList<VocabularyAnsModel>
    var id: String? = null
    private var createPos: Int = 0
    private var currentPos: Int = 0
    private var mUser: UserModel? = null
    private var point: Int = 0
    private lateinit var email: String
    private lateinit var presenter: VocabularyContract.Presenter
    private lateinit var shakeAnimation: Animation
    private lateinit var zoomAnimation: Animation
    private lateinit var zoomImgAnimation: Animation
    private lateinit var zoomCharacterAnimation: Animation
    private lateinit var dialog:AlertDialog
    private var mediaPlayer:MediaPlayer? = null
    private var str: String = ""
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vocabulary)
        getDataFromIntent()
        init()
        setOnClickListener()
        setupPresenterAndFetchUser()
        btnCheck()
        setData(createPos)
        btnQuit()
        btnRestart()
        btnSpeaker()
        tvNumQuestion.text = "/ " + mListQues.size.toString() + " "
    }
    private fun getDataFromIntent(){
        val intent = intent
        id = intent.getStringExtra("topic").toString()
        email = intent.getStringExtra("email").toString()
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
                mUser?.id?.let { presenter.updatePoint(it, point) }
                finish()
            }
        }
    }
    private fun btnSpeaker() {
        btnSpeaker.setOnClickListener {
            if(mediaPlayer != null && mediaPlayer!!.isPlaying)
            {
                mediaPlayer!!.stop()
                freeUpResources()
            }
            val audioBytes = mListAns[currentPos].audio
            mediaPlayer = byteArrayToAudio(this@VocabularyActivity, audioBytes)
            playSound()
        }
    }
    private fun freeUpResources(){
        mediaPlayer!!.release()
        mediaPlayer = null
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
    private fun btnCheck() {
        btnCheck.setOnClickListener {
            if (tvShow.text.isEmpty()) {
                tvShow.startAnimation(shakeAnimation)
            } else {
                presenter.checkAnswer(tvShow, mListQues, mListAns, createPos++, point)
            }
        }
    }
    private fun btnRestart() {
        btnRestart.setOnClickListener {
            setData(currentPos)
        }
    }
    private fun setData(pos: Int){
        if(pos >= 0 && pos < mListQues.size){
            setTvEnable()
            val charArray = mListQues[pos].vocaChar.toCharArray()
            tvQuestion.text = mListQues[pos].mean
            Glide.with(this)
                .load(mListQues[pos].img)
                .apply(RequestOptions().placeholder(android.R.drawable.ic_menu_gallery))
                .into(imgOb)
            imgOb.startAnimation(zoomImgAnimation)
            for (i in charArray.indices) {
                when (i) {
                    0 -> tvCharacter1.text = charArray[i].toString()
                    1 -> tvCharacter2.text = charArray[i].toString()
                    2 -> tvCharacter3.text = charArray[i].toString()
                    3 -> tvCharacter4.text = charArray[i].toString()
                    4 -> tvCharacter5.text = charArray[i].toString()
                    5 -> tvCharacter6.text = charArray[i].toString()
                    6 -> tvCharacter7.text = charArray[i].toString()
                    7 -> tvCharacter8.text = charArray[i].toString()
                    8 -> tvCharacter9.text = charArray[i].toString()
                    9 -> tvCharacter10.text = charArray[i].toString()
                    10 -> tvCharacter11.text = charArray[i].toString()
                    11 -> tvCharacter12.text = charArray[i].toString()
                }
            }
            tvCharacter1.visibility = View.VISIBLE
            tvCharacter2.visibility = View.VISIBLE
            tvCharacter3.visibility = View.VISIBLE
            tvCharacter4.visibility = View.VISIBLE
            tvCharacter5.visibility = View.VISIBLE
            tvCharacter6.visibility = View.VISIBLE
            tvCharacter7.visibility = View.VISIBLE
            tvCharacter8.visibility = View.VISIBLE
            tvCharacter9.visibility = View.VISIBLE
            tvCharacter10.visibility = View.VISIBLE
            tvCharacter11.visibility = View.VISIBLE
            tvCharacter12.visibility = View.VISIBLE
            tvShow.text = ""
            str = ""
            tvShow.setBackgroundResource(R.drawable.pink_boder)
        }else{
            finish()
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setTvEnable(){
        tvCharacter1.isEnabled = true
        tvCharacter2.isEnabled = true
        tvCharacter3.isEnabled = true
        tvCharacter4.isEnabled = true
        tvCharacter5.isEnabled = true
        tvCharacter6.isEnabled = true
        tvCharacter7.isEnabled = true
        tvCharacter8.isEnabled = true
        tvCharacter9.isEnabled = true
        tvCharacter10.isEnabled = true
        tvCharacter11.isEnabled = true
        tvCharacter12.isEnabled = true
    }
    private fun init(){
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake)
        zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom)
        zoomImgAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_img)
        zoomCharacterAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_character)
        mListQues = arrayListOf()
        mListAns = arrayListOf()

        tvCharacter1 = findViewById(R.id.tv_character_1)
        tvCharacter2 = findViewById(R.id.tv_character_2)
        tvCharacter3 = findViewById(R.id.tv_character_3)
        tvCharacter4 = findViewById(R.id.tv_character_4)
        tvCharacter5 = findViewById(R.id.tv_character_5)
        tvCharacter6 = findViewById(R.id.tv_character_6)
        tvCharacter7 = findViewById(R.id.tv_character_7)
        tvCharacter8 = findViewById(R.id.tv_character_8)
        tvCharacter9 = findViewById(R.id.tv_character_9)
        tvCharacter10 = findViewById(R.id.tv_character_10)
        tvCharacter11 = findViewById(R.id.tv_character_11)
        tvCharacter12 = findViewById(R.id.tv_character_12)
        tvShow = findViewById(R.id.tv_show)
        imgOb = findViewById(R.id.image_ob)
        tvQuestion = findViewById(R.id.tv_Question)
        btnCheck = findViewById(R.id.btn_check)
        btnSpeaker = findViewById(R.id.img_speaker)
        tvNumQuestion = findViewById(R.id.tv_numQuestion)
        tvNumQuesCurent = findViewById(R.id.tv_numQuestionCurrent)
        btnQuit = findViewById(R.id.btn_quit)
        btnRestart = findViewById(R.id.img_restart)
        tvPoint = findViewById(R.id.tv_point)
    }
    private fun setupPresenterAndFetchUser(){
        val vocaRepository = DBHelperRepository(this)
        presenter = VocabularyPresenter(applicationContext, this@VocabularyActivity,vocaRepository)
        presenter.getItemsVocabularyQuestion()
        presenter.getItemsVocabularyAnswer()
        presenter.getUser(email, object : TaskCallback.TaskCallbackUser2 {
            override fun onListUserLoaded(user: UserModel) {
                mUser = user
                point = user.point!!
                tvPoint.text = user.point.toString()
                Log.d("abssc", mUser!!.email.toString() + " " + mUser!!.point.toString())
            }
        })

    }
    private fun setOnClickListener(){
        tvCharacter1.setOnClickListener(this)
        tvCharacter2.setOnClickListener(this)
        tvCharacter3.setOnClickListener(this)
        tvCharacter4.setOnClickListener(this)
        tvCharacter5.setOnClickListener(this)
        tvCharacter6.setOnClickListener(this)
        tvCharacter7.setOnClickListener(this)
        tvCharacter8.setOnClickListener(this)
        tvCharacter9.setOnClickListener(this)
        tvCharacter10.setOnClickListener(this)
        tvCharacter11.setOnClickListener(this)
        tvCharacter12.setOnClickListener(this)
    }

    override fun showNextQuestion(listQuestion: ArrayList<VocabularyQuesModel>, listAnswer: ArrayList<VocabularyAnsModel>, newCurrentPos: Int) {
        currentPos = newCurrentPos
        setData(newCurrentPos)
        setOnClickListener()
    }
    override fun showVocabularyQuestion(mListQuestion: ArrayList<VocabularyQuesModel>) {
        mListQues = mListQuestion
        val charArray = mListQuestion[createPos].vocaChar.toCharArray()
        tvQuestion.text = mListQuestion[createPos].mean
        Glide.with(this)
            .load(mListQuestion[createPos].img)
            .into(imgOb)
        for (i in charArray.indices) {
            when (i) {
                0 -> tvCharacter1.text = charArray[i].toString()
                1 -> tvCharacter2.text = charArray[i].toString()
                2 -> tvCharacter3.text = charArray[i].toString()
                3 -> tvCharacter4.text = charArray[i].toString()
                4 -> tvCharacter5.text = charArray[i].toString()
                5 -> tvCharacter6.text = charArray[i].toString()
                6 -> tvCharacter7.text = charArray[i].toString()
                7 -> tvCharacter8.text = charArray[i].toString()
                8 -> tvCharacter9.text = charArray[i].toString()
                9 -> tvCharacter10.text = charArray[i].toString()
                10 -> tvCharacter11.text = charArray[i].toString()
                11 -> tvCharacter12.text = charArray[i].toString()
            }
        }
        setOnClickListener()
    }

    override fun showVocabularyAnswer(mListAnswer: ArrayList<VocabularyAnsModel>) {
        mListAns = mListAnswer
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

    override fun showResult(isCorrect: Boolean, textview: TextView) {
        if(isCorrect){
            mediaPlayer = MediaPlayer.create(this, R.raw.true_)
            playSound()
            handler.postDelayed({
                textview.setBackgroundResource(R.drawable.bg_green_corner_30)
                textview.startAnimation(zoomAnimation)
            }, 200)
        }else {
            mediaPlayer = MediaPlayer.create(this, R.raw.false_)
            playSound()
            handler.postDelayed({
                textview.setBackgroundResource(R.drawable.bg_red_corner_10)
                textview.startAnimation(shakeAnimation)
            }, 200)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun showNumQuesCurent(pos: Int) {
        tvNumQuesCurent.text = "$pos"
    }

    override fun showPoint(point: Int) {
        this.point = point
        tvPoint.text = point.toString()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.tv_character_1 -> {
                    tvCharacter1.startAnimation(zoomCharacterAnimation)
                    tvCharacter1.isEnabled = false
                    tvCharacter1.visibility = View.GONE
                    val textToAdd = tvCharacter1.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_2 -> {
                    tvCharacter2.startAnimation(zoomCharacterAnimation)
                    tvCharacter2.isEnabled = false
                    tvCharacter2.visibility = View.GONE
                    val textToAdd = tvCharacter2.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_3 -> {
                    tvCharacter3.startAnimation(zoomCharacterAnimation)
                    tvCharacter3.isEnabled = false
                    tvCharacter3.visibility = View.GONE
                    val textToAdd = tvCharacter3.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_4 -> {
                    tvCharacter4.startAnimation(zoomCharacterAnimation)
                    tvCharacter4.isEnabled = false
                    tvCharacter4.visibility = View.GONE
                    val textToAdd = tvCharacter4.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_5 -> {
                    tvCharacter5.startAnimation(zoomCharacterAnimation)
                    tvCharacter5.isEnabled = false
                    tvCharacter5.visibility = View.GONE
                    val textToAdd = tvCharacter5.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_6 -> {
                    tvCharacter6.startAnimation(zoomCharacterAnimation)
                    tvCharacter6.isEnabled = false
                    tvCharacter6.visibility = View.GONE
                    val textToAdd = tvCharacter6.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_7 -> {
                    tvCharacter7.startAnimation(zoomCharacterAnimation)
                    tvCharacter7.isEnabled = false
                    tvCharacter7.visibility = View.GONE
                    val textToAdd = tvCharacter7.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_8 -> {
                    tvCharacter8.startAnimation(zoomCharacterAnimation)
                    tvCharacter8.isEnabled = false
                    tvCharacter8.visibility = View.GONE
                    val textToAdd = tvCharacter8.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_9 -> {
                    tvCharacter9.startAnimation(zoomCharacterAnimation)
                    tvCharacter9.isEnabled = false
                    tvCharacter9.visibility = View.GONE
                    val textToAdd = tvCharacter9.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_10 -> {
                    tvCharacter10.startAnimation(zoomCharacterAnimation)
                    tvCharacter10.isEnabled = false
                    tvCharacter10.visibility = View.GONE
                    val textToAdd = tvCharacter10.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_11 -> {
                    tvCharacter11.startAnimation(zoomCharacterAnimation)
                    tvCharacter11.isEnabled = false
                    tvCharacter11.visibility = View.GONE
                    val textToAdd = tvCharacter11.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_12 -> {
                    tvCharacter12.startAnimation(zoomCharacterAnimation)
                    tvCharacter12.isEnabled = false
                    tvCharacter12.visibility = View.GONE
                    val textToAdd = tvCharacter12.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
            }
        }
    }
}