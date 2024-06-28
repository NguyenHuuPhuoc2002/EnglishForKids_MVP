package com.example.learnenglish.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.learnenglish.R
import com.example.learnenglish.activity.SkillActivity
import com.example.learnenglish.activity.TopicActivity
import com.example.learnenglish.adapter.SkillAdapter
import com.example.learnenglish.contract.QuizzesContract
import com.example.learnenglish.contract.SkillContract
import com.example.learnenglish.databinding.FragmentSkillBinding
import com.example.learnenglish.model.SkillModel
import com.example.learnenglish.presenter.SkillPresenter
import com.example.learnenglish.repository.DBHelperRepository

class SkillFragment : Fragment(), SkillContract.View {
    private lateinit var binding: FragmentSkillBinding
    private lateinit var mListHome: ArrayList<SkillModel>
    private lateinit var presenter: SkillContract.Presenter
    private var email: String? = null
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDataFromIntent()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSkillBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        addDataHome()
        setAdapterHome()

    }

    private fun init() {
        val alertDialog = AlertDialog.Builder(context)
        val progressBar = ProgressBar(context)

        alertDialog.setView(progressBar)
        alertDialog.setTitle("Đang thực hiện!")
        alertDialog.setCancelable(false)
        dialog = alertDialog.create()
    }

    private fun getDataFromIntent() {
        arguments?.let {
            email = it.getString("email")
        }
    }

    private fun setAdapterHome() {
        val taskRepository = DBHelperRepository(requireActivity())
        presenter = SkillPresenter(this, requireActivity() as SkillActivity, email!!, taskRepository)
        val adapter = SkillAdapter(mListHome, object : QuizzesContract.OnClickListener {
            override fun onClickListenerItemHome(pos: Int) {
                presenter.onStartActivity(mListHome[pos], email!!)
            }
        })
        binding.rcvHome.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        binding.rcvHome.adapter = adapter
    }

    private fun addDataHome() {
        mListHome = arrayListOf()
        mListHome.add(SkillModel(R.drawable.img_tracnghiem, "Trắc Nghiệm"))
        mListHome.add(SkillModel(R.drawable.img_tracnghiem, "Từ Vựng"))
        mListHome.add(SkillModel(R.drawable.img_tracnghiem, "Luyện Nghe"))
        mListHome.add(SkillModel(R.drawable.img_tracnghiem, "Sắp Xếp Câu"))
    }

    override fun showTopicActivity(title: String, email: String) {
        val intent = Intent(requireActivity(), TopicActivity::class.java)
        intent.putExtra("title_skill", title)
        intent.putExtra("email", email)
        startActivity(intent)
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
