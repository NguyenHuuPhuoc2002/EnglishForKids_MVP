package com.example.learnenglish.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.learnenglish.adapter.RankAdapter
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.databinding.FragmentRankBinding
import com.example.learnenglish.model.UserModel
import com.example.learnenglish.presenter.RankPresenter
import com.example.learnenglish.repository.DBHelperRepository

class RankFragment : Fragment() {
    private lateinit var binding: FragmentRankBinding
    private var email: String? = null
    private lateinit var presenter: RankPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDataFromIntent()
    }

    private fun getDataFromIntent() {
        arguments?.let {
            email = it.getString("email").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankBinding.inflate(inflater, container, false)
        seUpPresenter()
        return binding.root
    }

    private fun seUpPresenter() {
        val taskRepository = DBHelperRepository(requireContext())
        presenter = RankPresenter(taskRepository)
        presenter.getUser(email!!, object : TaskCallback.TaskCallbackUserRankList{
            override fun onListUserLoadedRank(mListUser: ArrayList<UserModel>) {
                val newList = mListUser.sortedWith(compareByDescending { it.point })
                if(newList.isNotEmpty()){
                    binding.tvPoint1.text = newList[0].point.toString()
                }
                if(newList.size == 2){
                    binding.tvPoint1.text = newList[0].point.toString()
                    binding.tvPoint2.text = newList[1].point.toString()
                }
                if(newList.size == 3){
                    binding.tvPoint1.text = newList[0].point.toString()
                    binding.tvPoint2.text = newList[1].point.toString()
                    binding.tvPoint2.text = newList[2].point.toString()
                }
                val adapter = RankAdapter(email!!, newList)
                binding.rcvRank.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
                binding.rcvRank.adapter = adapter
            }
        })
    }


}