package com.example.learnenglish.activity

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.view.View
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.learnenglish.R
import com.example.learnenglish.contract.VocabularyContract
import com.example.learnenglish.model.VocabularyAnsModel
import com.example.learnenglish.model.VocabularyQuesModel
import com.example.learnenglish.presenter.VocabularyPresenter
import com.example.learnenglish.repository.DBHelperRepository

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
    private lateinit var btnQuit: Button
    lateinit var tvNumQuesCurent: TextView
    private lateinit var imgOb: ImageView
    private lateinit var tvQuestion: TextView
    private lateinit var btnCheck: Button
    private lateinit var tvShow: TextView
    private lateinit var mListQues: ArrayList<VocabularyQuesModel>
    private lateinit var mListAns: ArrayList<VocabularyAnsModel>
    var id: String? = null
    private var currentPos: Int = 0
    private lateinit var presenter: VocabularyContract.Presenter
    private lateinit var shakeAnimation: Animation

    private var str: String = ""
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vocabulary)
        val intent = intent
        id = intent.getStringExtra("topic").toString()
        init()
        setOnClickListener()
        getData()
        btnCheck()
        setData(currentPos)
        btnQuit()
        tvNumQuestion.text = " / " + mListQues.size.toString() + " "
    }
    private fun btnQuit() {
        btnQuit.setOnClickListener {
            val alertDialogBuider = AlertDialog.Builder(this)
            alertDialogBuider.setMessage("Bạn có chắc chắn muốn dừng bài học không?")

            alertDialogBuider.setPositiveButton("Có"){ dialog: DialogInterface, _: Int ->
                startActivity(Intent(this@VocabularyActivity, SkillActivity::class.java))
            }
            alertDialogBuider.setNegativeButton("Không") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
            alertDialogBuider.setCancelable(false)
            val alertDialog = alertDialogBuider.create()
            alertDialog.show()
        }
    }
    private fun btnCheck() {
        btnCheck.setOnClickListener {
            if (tvShow.text.isEmpty()) {
                tvShow.startAnimation(shakeAnimation)
            } else {
                presenter.checkAnswer(tvShow, mListQues, mListAns, currentPos++)
            }
        }
    }
    private fun setData(pos: Int){
        val charArray = mListQues[pos].vocaChar.toCharArray()
        tvQuestion.text = mListQues[pos].mean
        Glide.with(this)
            .load(mListQues[pos].img)
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
    }
    private fun init(){
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake)
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
        tvNumQuestion = findViewById(R.id.tv_numQuestion)
        tvNumQuesCurent = findViewById(R.id.tv_numQuestionCurrent)
        btnQuit = findViewById(R.id.btn_quit)
    }
    private fun getData(){
        val vocaRepository = DBHelperRepository(this)
        presenter = VocabularyPresenter(applicationContext, this@VocabularyActivity,vocaRepository)
        presenter.getItemsVocabularyQuestion()
        presenter.getItemsVocabularyAnswer()

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

    override fun showNextQuestion(
        listQuestion: ArrayList<VocabularyQuesModel>,
        listAnswer: ArrayList<VocabularyAnsModel>,
        newCurrentPos: Int
    ) {

        val charArray = listQuestion[newCurrentPos].vocaChar.toCharArray()
        tvQuestion.text = listQuestion[newCurrentPos].mean
        Glide.with(this)
            .load(listQuestion[newCurrentPos].img)
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
        setData(newCurrentPos)
        setOnClickListener()
    }
    override fun showVocabularyQuestion(mListQuestion: ArrayList<VocabularyQuesModel>) {
        mListQues = mListQuestion
        val charArray = mListQuestion[currentPos].vocaChar.toCharArray()
        tvQuestion.text = mListQuestion[currentPos].mean
        Glide.with(this)
            .load(mListQuestion[currentPos].img)
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
        startActivity(intent)
    }

    override fun showResult(isCorrect: Boolean, textview: TextView) {
        if(isCorrect){
            handler.postDelayed({
                textview.setBackgroundResource(R.drawable.bg_green_corner_30)
            }, 200)
        }else {
            handler.postDelayed({
                textview.setBackgroundResource(R.drawable.bg_red_corner_10)
                textview.startAnimation(shakeAnimation)
            }, 200)
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.tv_character_1 -> {
                    tvCharacter1.visibility = View.GONE
                    val textToAdd = tvCharacter1.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_2 -> {
                    tvCharacter2.visibility = View.GONE
                    val textToAdd = tvCharacter2.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_3 -> {
                    tvCharacter3.visibility = View.GONE
                    val textToAdd = tvCharacter3.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_4 -> {
                    tvCharacter4.visibility = View.GONE
                    val textToAdd = tvCharacter4.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_5 -> {
                    tvCharacter5.visibility = View.GONE
                    val textToAdd = tvCharacter5.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_6 -> {
                    tvCharacter6.visibility = View.GONE
                    val textToAdd = tvCharacter6.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_7 -> {
                    tvCharacter7.visibility = View.GONE
                    val textToAdd = tvCharacter7.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_8 -> {
                    tvCharacter8.visibility = View.GONE
                    val textToAdd = tvCharacter8.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_9 -> {
                    tvCharacter9.visibility = View.GONE
                    val textToAdd = tvCharacter9.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_10 -> {
                    tvCharacter10.visibility = View.GONE
                    val textToAdd = tvCharacter10.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_11 -> {
                    tvCharacter11.visibility = View.GONE
                    val textToAdd = tvCharacter11.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
                R.id.tv_character_12 -> {
                    tvCharacter12.visibility = View.GONE
                    val textToAdd = tvCharacter12.text.toString()
                    str += textToAdd
                    tvShow.text = str
                }
            }
        }
    }
}