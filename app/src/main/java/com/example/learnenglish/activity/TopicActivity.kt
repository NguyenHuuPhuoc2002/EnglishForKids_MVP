package com.example.learnenglish.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learnenglish.R
import com.example.learnenglish.adapter.TopicAdapter
import com.example.learnenglish.contract.QuizzesContract
import com.example.learnenglish.contract.TopicContract
import com.example.learnenglish.model.TopicModel
import com.example.learnenglish.presenter.TopicPresenter
import com.example.learnenglish.repository.DBHelperRepository
import kotlin.properties.Delegates

class TopicActivity : AppCompatActivity(), TopicContract.View {
    private lateinit var rcvTopic: RecyclerView
    private lateinit var btnBack: ImageView
    private lateinit var str: String
    private lateinit var email: String
    private lateinit var presenter: TopicContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)

        init()
        getDataFromInten()
        setupPresenterAndFetchUser()
        btnBack()
        Log.d("email_topic", email)
    }
    private fun init(){
        rcvTopic = findViewById(R.id.rcv_topic)
        btnBack = findViewById(R.id.img_back_topic)
    }
    private fun getDataFromInten(){
        val intent = intent
        str = intent.getStringExtra("title_skill").toString()
        email = intent.getStringExtra("email").toString()
    }
    private fun setupPresenterAndFetchUser(){
        val taskRepository = DBHelperRepository(this)
        presenter = TopicPresenter(applicationContext, this@TopicActivity,taskRepository)
        presenter.getItemsTopic()
    }
    private fun btnBack(){
        btnBack.setOnClickListener {
            finish()
        }
    }

    override fun showTopic(mListTopic: ArrayList<TopicModel>) {
        val adapter = TopicAdapter(mListTopic, object : QuizzesContract.OnClickListener{
            override fun onClickListenerItemHome(pos: Int) {
                presenter.onStartActivity(str, email, mListTopic[pos].id)
            }
        })
        rcvTopic.layoutManager = GridLayoutManager(this@TopicActivity, 1, GridLayoutManager.VERTICAL, false)
        rcvTopic.adapter = adapter
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showActivity(email: String, id: String, str: String) {
        when (str) {
            "Trắc Nghiệm" -> {
                val intent = Intent(this@TopicActivity, QuizzesActivity::class.java)
                intent.putExtra("topic", id)
                intent.putExtra("email", email)
                Log.d("emailTopic", email)
                startActivity(intent)
            }
            "Từ Vựng" -> {
                val intent = Intent(this@TopicActivity, VocabularyActivity::class.java)
                intent.putExtra("topic", id)
                intent.putExtra("email", email)
                Log.d("emailTopic", email)
                startActivity(intent)
            }
            "Luyện Nghe" -> {
                val intent = Intent(this@TopicActivity, ListenActivity::class.java)
                intent.putExtra("topic", id)
                intent.putExtra("email", email)
                Log.d("emailTopic", email)
                startActivity(intent)
            }
            "Sắp Xếp Câu" -> {
                val intent = Intent(this@TopicActivity, SentencesSortActivity::class.java)
                intent.putExtra("topic", id)
                intent.putExtra("email", email)
                Log.d("emailTopic", email)
                startActivity(intent)
            }
            else -> Toast.makeText(this@TopicActivity, "Hoạt động không được hỗ trợ!", Toast.LENGTH_SHORT).show()
        }
    }


}