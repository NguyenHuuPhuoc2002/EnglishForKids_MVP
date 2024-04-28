package com.example.learnenglish.activity

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

class TopicActivity : AppCompatActivity(), TopicContract.View {
    private lateinit var rcvTopic: RecyclerView
    private lateinit var btnBack: ImageView
    private lateinit var str: String
    private lateinit var presenter: TopicContract.Presenter
    private lateinit var mList: ArrayList<TopicModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic)
        rcvTopic = findViewById(R.id.rcv_topic)
        btnBack = findViewById(R.id.img_back_topic)
        mList = arrayListOf()
        getData()
        val intent = intent
        str = intent.getStringExtra("title").toString()
        btnBack()
    }
    private fun getData(){
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
                if(str == "Trắc Nghiệm"){
                    val intent = Intent(this@TopicActivity, QuizzesActivity::class.java)
                    intent.putExtra("topic", mListTopic[pos].id)
                    startActivity(intent)
                }else if(str == "Từ Vựng"){
                    val intent = Intent(this@TopicActivity, VocabularyActivity::class.java)
                    intent.putExtra("topic", mListTopic[pos].id)
                    startActivity(intent)
                }else if(str == "Luyện Nghe"){
                    val intent = Intent(this@TopicActivity, ListenActivity::class.java)
                    intent.putExtra("topic", mListTopic[pos].id)
                    startActivity(intent)
                }
            }
        })
        rcvTopic.layoutManager = GridLayoutManager(this@TopicActivity, 1, GridLayoutManager.VERTICAL, false)
        rcvTopic.adapter = adapter
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}