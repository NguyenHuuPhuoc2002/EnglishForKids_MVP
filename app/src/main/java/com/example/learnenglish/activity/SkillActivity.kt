package com.example.learnenglish.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learnenglish.R
import com.example.learnenglish.adapter.SkillAdapter
import com.example.learnenglish.contract.QuizzesContract
import com.example.learnenglish.contract.SkillContract
import com.example.learnenglish.model.SkillModel
import com.example.learnenglish.presenter.LogInPresenter
import com.example.learnenglish.presenter.SkillPresenter

class SkillActivity : AppCompatActivity(), SkillContract.View{
    private lateinit var rcvHome: RecyclerView
    private lateinit var mListHome: ArrayList<SkillModel>
    private lateinit var presenter: SkillContract.Presenter
    private lateinit var email: String
    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill)
        rcvHome = findViewById(R.id.rcv_home)
        addDataHome()
        setAdapterHome()
        getDataFromInten()
        presenter = SkillPresenter(this@SkillActivity, email)
        Log.d("email_skill", email)
    }

    private fun getDataFromInten(){
        val intent = intent
        email = intent.getStringExtra("email").toString()
    }
    private fun setAdapterHome(){
        val adapter = SkillAdapter(mListHome, object : QuizzesContract.OnClickListener{
            override fun onClickListenerItemHome(pos: Int) {
                presenter.onStartActivity(mListHome[pos], email)
            }
        })
        rcvHome.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        rcvHome.adapter = adapter

    }
    private fun addDataHome(){
        mListHome = arrayListOf()
        mListHome.add(SkillModel(R.drawable.img_tracnghiem, "Trắc Nghiệm"))
        mListHome.add(SkillModel(R.drawable.img_tracnghiem, "Từ Vựng"))
        mListHome.add(SkillModel(R.drawable.img_tracnghiem, "Luyện Nghe"))
        mListHome.add(SkillModel(R.drawable.img_tracnghiem, "Sắp Xếp Câu"))
    }

    override fun showTopicActivity(title: String, email: String) {
        val intent = Intent(this@SkillActivity, TopicActivity::class.java)
        intent.putExtra("title_skill", title)
        intent.putExtra("email", email)
        startActivity(intent)
    }

}