package com.example.learnenglish.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learnenglish.R
import com.example.learnenglish.adapter.SkillAdapter
import com.example.learnenglish.contract.QuizzesContract
import com.example.learnenglish.model.SkillModel

class SkillActivity : AppCompatActivity(){
    private lateinit var rcvHome: RecyclerView
    private lateinit var mListHome: ArrayList<SkillModel>
    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill)
        rcvHome = findViewById(R.id.rcv_home)
        addDataHome()
        setAdapterHome()

    }
    private fun setAdapterHome(){
        val adapter = SkillAdapter(mListHome, object : QuizzesContract.OnClickListener{
            override fun onClickListenerItemHome(pos: Int) {
                if(mListHome[pos].title == "Trắc Nghiệm"){
                    val intent = Intent(this@SkillActivity, TopicActivity::class.java)
                    intent.putExtra("title", mListHome[pos].title)
                    startActivity(intent)
                }else if(mListHome[pos].title == "Từ Vựng"){
                    val intent = Intent(this@SkillActivity, TopicActivity::class.java)
                    intent.putExtra("title", mListHome[pos].title)
                    startActivity(intent)
                }else if(mListHome[pos].title == "Luyện Nghe"){
                    val intent = Intent(this@SkillActivity, TopicActivity::class.java)
                    intent.putExtra("title", mListHome[pos].title)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@SkillActivity, "Chưa làm !", Toast.LENGTH_SHORT).show()
                }
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
}