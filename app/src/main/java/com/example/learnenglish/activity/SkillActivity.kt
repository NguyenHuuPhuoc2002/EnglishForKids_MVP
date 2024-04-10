package com.example.learnenglish.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learnenglish.R
import com.example.learnenglish.adapter.SkillAdapter
import com.example.learnenglish.contract.TaskContract
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
        val adapter = SkillAdapter(mListHome, object : TaskContract.OnClickListener{
            override fun onClickListenerItemHome(pos: Int) {
                if(mListHome[pos].title == "Trắc Nghiệm"){
                    startActivity(Intent(this@SkillActivity, TopicActivity::class.java))
                }else{
                    startActivity(Intent(this@SkillActivity, TopicActivity::class.java))
                }
            }
        })
        rcvHome.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        rcvHome.adapter = adapter

    }
    private fun addDataHome(){
        mListHome = arrayListOf()
        mListHome.add(SkillModel(R.drawable.img_tracnghiem, "Trắc Nghiệm"))
        mListHome.add(SkillModel(R.drawable.img_tracnghiem, "Nghe"))
        mListHome.add(SkillModel(R.drawable.img_tracnghiem, "Điền Khuyết"))
        mListHome.add(SkillModel(R.drawable.img_tracnghiem, "Sắp Xếp Câu"))
    }
}