package com.example.learnenglish.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.learnenglish.R
import com.example.learnenglish.contract.InfoContract
import com.example.learnenglish.contract.SkillContract
import com.example.learnenglish.contract.SkillContractV2
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.databinding.FragmentInfoBinding
import com.example.learnenglish.model.UserModel
import com.example.learnenglish.presenter.InfoPresenter
import com.example.learnenglish.repository.DBHelperRepository

class InfoFragment : Fragment(), InfoContract.View {
    private lateinit var binding: FragmentInfoBinding
    private lateinit var presenter: InfoPresenter
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val taskRepo = DBHelperRepository(requireContext())
        presenter = InfoPresenter(this, taskRepo)
        presenter.getUser(email!!, object : TaskCallback.TaskCallbackUserRank{
            override fun onListUserLoaded(user: UserModel, rank: Int) {
                binding.tvPoint.text = user.point.toString()
                binding.tvName.text = user.name.toString()
                binding.tvEmail.text = user.email.toString()
                binding.tvRank.text = rank.toString()
                binding.btnUpdate.setOnClickListener {
                    val edtName = binding.edtName.text.toString()
                    presenter.update(user.id!!, edtName)
                    binding.edtName.setText("")
                }
            }

        })
    }
    private fun getDataFromIntent() {
        arguments?.let {
            email = it.getString("email")
        }
    }
    override fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccesMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showFailMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showTextBlankMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}