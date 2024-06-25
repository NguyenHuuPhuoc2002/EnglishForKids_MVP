package com.example.learnenglish.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.learnenglish.R
import com.example.learnenglish.contract.SentencesSortContract
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.databinding.ActivitySentencesSortBinding
import com.example.learnenglish.model.SentencesSortAnswerModel
import com.example.learnenglish.model.SentencesSortQuesModel
import com.example.learnenglish.model.UserModel
import com.example.learnenglish.presenter.SentencesSortPresenter
import com.example.learnenglish.repository.DBHelperRepository

class SentencesSortActivity : AppCompatActivity(), SentencesSortContract.View, View.OnClickListener {
    companion object {
        val handler = Handler(Looper.getMainLooper())
    }
    private lateinit var binding: ActivitySentencesSortBinding
    private lateinit var mListQues: ArrayList<SentencesSortQuesModel>
    private lateinit var mListAns: ArrayList<SentencesSortAnswerModel>
    private lateinit var presenter: SentencesSortContract.Presenter
    private lateinit var shakeAnimation: Animation
    private lateinit var zoomAnimation: Animation
    private lateinit var zoomImgAnimation: Animation
    private lateinit var zoomCharacterAnimation: Animation
    private var currentPos: Int = 0
    private var createPos: Int = 0
    private var mUser: UserModel? = null
    private var point: Int = 0
    private lateinit var dialog: AlertDialog
    private lateinit var email: String
    private var mediaPlayer: MediaPlayer? = null
    var id: String? = null
    private var str: String = ""
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySentencesSortBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        getDataFromIntent()
        setupPresenterAndFetchUser()
        setOnClickListener()
        bintCheck()
        btnQuit()
        btnRestart()
        binding.tvNumQuestion.text = " / " + mListQues.size.toString() + " "

    }

    private fun btnRestart() {
        binding.imgRestart.setOnClickListener {
            setData(currentPos)
        }
    }
    private fun getDataFromIntent(){
        val intent = intent
        id = intent.getStringExtra("topic").toString()
        email = intent.getStringExtra("email").toString()
    }

    private fun btnQuit() {
        binding.btnQuit.setOnClickListener {
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

    private fun playSound() {
        mediaPlayer?.start()
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
    private fun bintCheck() {
        binding.btnCheck.setOnClickListener {
            binding.btnCheck.isEnabled = false
            if(binding.tvAnswerShow.text.isEmpty()){
                binding.tvAnswerShow.startAnimation(shakeAnimation)
                binding.btnCheck.isEnabled = true
            }else{
                for(i in 0 until mListAns.size){
                    if(mListQues[currentPos].answerID == mListAns[i].answerID){
                        presenter.checkAnswer(binding.tvAnswerShow, mListQues, mListAns[i], createPos++, point )
                        Log.d("isCorrect", mListAns[i].isCorrect)
                        break;
                    }
                }
            }

        }
    }

    private fun setData(pos: Int){
        if(pos >= 0 && pos < mListQues.size ){
            binding.tvQuestion.text = mListQues[pos].content
            for(i in 0 until mListAns.size){
                if(mListQues[pos].answerID == mListAns[i].answerID){
                    binding.tvAnswerA.text = mListAns[i].answerA
                    binding.tvAnswerB.text = mListAns[i].answerB
                    binding.tvAnswerC.text = mListAns[i].answerC
                    binding.tvAnswerD.text = mListAns [i].answerD
                    break;
                }
            }
            binding.tvAnswerA.visibility = View.VISIBLE
            binding.tvAnswerB.visibility = View.VISIBLE
            binding.tvAnswerC.visibility = View.VISIBLE
            binding.tvAnswerD.visibility = View.VISIBLE

            binding.btnCheck.isEnabled = true
            binding.tvAnswerA.isEnabled = true
            binding.tvAnswerB.isEnabled = true
            binding.tvAnswerC.isEnabled = true
            binding.tvAnswerD.isEnabled = true

            str = ""
            binding.tvAnswerShow.text = ""
            binding.tvAnswerShow.setBackgroundResource(R.drawable.pink_boder)
        }else{
            finish()
            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show()
        }
    }
    private fun init() {
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake)
        zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom)
        zoomImgAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_img)
        zoomCharacterAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_character)
        mListQues = arrayListOf()
        mListAns = arrayListOf()
    }
    private fun setupPresenterAndFetchUser(){
        val taskRepository = DBHelperRepository(this)
        presenter = SentencesSortPresenter(applicationContext, this@SentencesSortActivity, taskRepository)
        presenter.getItemsQuestion()
        presenter.getItemsAnswer()
        presenter.getUser(email, object : TaskCallback.TaskCallbackUser2 {
            override fun onListUserLoaded(user: UserModel) {
                mUser = user
                point = user.point!!
                binding.tvPoint.text = user.point.toString()
                Log.d("abssc", mUser!!.email.toString() + " " + mUser!!.point.toString())
            }
        })
    }
    private fun setOnClickListener(){

        binding.tvAnswerA.setBackgroundResource(R.drawable.bg_customitem)
        binding.tvAnswerB.setBackgroundResource(R.drawable.bg_customitem)
        binding.tvAnswerC.setBackgroundResource(R.drawable.bg_customitem)
        binding.tvAnswerD.setBackgroundResource(R.drawable.bg_customitem)

        binding.tvAnswerA.setOnClickListener(this)
        binding.tvAnswerB.setOnClickListener(this)
        binding.tvAnswerC.setOnClickListener(this)
        binding.tvAnswerD.setOnClickListener(this)
    }
    override fun showQuestion(mListQuestion: ArrayList<SentencesSortQuesModel>) {
        mListQues = mListQuestion
        binding.tvQuestion.text = mListQuestion[currentPos].content
    }

    override fun showAnswer(mListAnswer: ArrayList<SentencesSortAnswerModel>) {
        mListAns = mListAnswer
        for(i in 0 until mListAnswer.size){
            if(mListAnswer[i].answerID == 4){
                binding.tvAnswerA.text = mListAnswer[i].answerA
                binding.tvAnswerB.text = mListAnswer[i].answerB
                binding.tvAnswerC.text = mListAnswer[i].answerC
                binding.tvAnswerD.text = mListAnswer [i].answerD
                Log.d("id", mListQues[createPos].answerID.toString() + mListAnswer[i].answerID.toString() )
                break;
            }
        }
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showActivityFinished(
        totalNumberOfQuestion: Int,
        numCorrectAnswer: Int,
        point: Int
    ) {
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
        }else{
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
        binding.tvNumQuestionCurrent.text = "$pos"
    }

    override fun showPoint(point: Int) {
        this.point = point
        binding.tvPoint.text = point.toString()
    }

    override fun showNextQuestion(
        listQuestion: ArrayList<SentencesSortQuesModel>,
        mAnswer: SentencesSortAnswerModel,
        newCurrentPos: Int
    ) {
        currentPos = newCurrentPos

        setData(newCurrentPos)
        setOnClickListener()
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        if(v != null){
            when (v.id){
                R.id.tv_answer_A -> {
                    binding.tvAnswerA.isEnabled = false
                    binding.tvAnswerA.startAnimation(zoomCharacterAnimation)
                    val textToAdd = binding.tvAnswerA.text.toString() + " "
                    str += textToAdd
                    binding.tvAnswerA.visibility = View.GONE
                    binding.tvAnswerShow.text = str
                }
                R.id.tv_answer_B ->{
                    binding.tvAnswerB.isEnabled = false
                    binding.tvAnswerB.startAnimation(zoomCharacterAnimation)
                    val textToAdd = binding.tvAnswerB.text.toString() + " "
                    str += textToAdd
                    binding.tvAnswerB.visibility = View.GONE
                    binding.tvAnswerShow.text = str
                }
                R.id.tv_answer_C ->{
                    binding.tvAnswerC.isEnabled = false
                    binding.tvAnswerC.startAnimation(zoomCharacterAnimation)
                    val textToAdd = binding.tvAnswerC.text.toString() + " "
                    str += textToAdd
                    binding.tvAnswerC.visibility = View.GONE
                    binding.tvAnswerShow.text = str
                }
                R.id.tv_answer_D ->{
                    binding.tvAnswerD.isEnabled = false
                    binding.tvAnswerD.startAnimation(zoomCharacterAnimation)
                    val textToAdd = binding.tvAnswerD.text.toString() + " "
                    str += textToAdd
                    binding.tvAnswerD.visibility = View.GONE
                    binding.tvAnswerShow.text = str
                }
            }
        }
    }

}