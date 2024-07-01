package com.example.learnenglish.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.learnenglish.activity.SkillActivity
import com.example.learnenglish.activity.TopicActivity
import com.example.learnenglish.contract.SkillContract
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.databinding.FragmentInfoBinding
import com.example.learnenglish.model.UserModel
import com.example.learnenglish.presenter.SkillPresenter
import com.example.learnenglish.repository.DBHelperRepository

class InfoFragment : Fragment(), SkillContract.View {
    private lateinit var binding: FragmentInfoBinding
    private lateinit var presenter: SkillContract.Presenter
    private var email: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDataFromIntent()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        setUpUI()
        return binding.root
    }

    private fun setUpUI() {
        binding.btnUpdate.setOnClickListener {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPresenterAndFetchUser()
        Log.d("okokok", email.toString())
    }
    private fun setupPresenterAndFetchUser(){
        val taskRepository = DBHelperRepository(requireContext())
        presenter = SkillPresenter(SkillFragment(),requireActivity() as SkillActivity, email!!, taskRepository)
        presenter.getUser(email!!, object : TaskCallback.TaskCallbackUserRank{
            override fun onListUserLoaded(user: UserModel, rank: Int) {
               binding.tvName.text = user.name
               binding.tvEmail.text = user.email
               binding.tvPoint.text = user.point.toString()
               binding.tvRank.text = rank.toString()
            }

        })
    }
    private fun getDataFromIntent() {
        arguments?.let {
            email = it.getString("email")
        }
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