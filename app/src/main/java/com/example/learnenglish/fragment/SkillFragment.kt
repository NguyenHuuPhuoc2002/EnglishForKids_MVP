package com.example.learnenglish.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.learnenglish.R
import com.example.learnenglish.activity.SkillActivity
import com.example.learnenglish.activity.TopicActivity
import com.example.learnenglish.adapter.SkillAdapter
import com.example.learnenglish.contract.QuizzesContract
import com.example.learnenglish.contract.SkillContract
import com.example.learnenglish.contract.SkillContractV2
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.databinding.FragmentRankBinding
import com.example.learnenglish.databinding.FragmentSkillBinding
import com.example.learnenglish.model.SkillModel
import com.example.learnenglish.model.UserModel
import com.example.learnenglish.presenter.SkillPresenter
import com.example.learnenglish.presenter.SkillPresenterV2
import com.example.learnenglish.repository.DBHelperRepository

class SkillFragment : Fragment(), SkillContractV2.View {
    private lateinit var binding: FragmentSkillBinding
    private lateinit var mListHome: ArrayList<SkillModel>
    private lateinit var presenter: SkillPresenterV2
    private var email: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDataFromIntent()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSkillBinding.inflate(inflater, container, false)
        addDataHome()
        setAdapterHome()
        return binding.root
    }
    private fun getDataFromIntent() {
        arguments?.let {
            email = it.getString("email").toString()
        }
    }

    private fun setAdapterHome(){
        presenter = SkillPresenterV2(this)
        val adapter = SkillAdapter(mListHome, object : QuizzesContract.OnClickListener{
            override fun onClickListenerItemHome(pos: Int) {
                presenter.onStartActivity(mListHome[pos], email!!)
            }
        })
        binding.rcvHome.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        binding.rcvHome.adapter = adapter

    }

    private fun addDataHome(){
        mListHome = arrayListOf()
        mListHome.add(SkillModel(R.drawable.img_tracnghiem, "Trắc Nghiệm"))
        mListHome.add(SkillModel(R.drawable.img_tracnghiem, "Từ Vựng"))
        mListHome.add(SkillModel(R.drawable.img_tracnghiem, "Luyện Nghe"))
        mListHome.add(SkillModel(R.drawable.img_tracnghiem, "Sắp Xếp Câu"))
    }

    override fun showTopicActivity(title: String, email: String) {
        val intent = Intent(requireContext(), TopicActivity::class.java)
        intent.putExtra("title_skill", title)
        intent.putExtra("email", email)
        startActivity(intent)
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}