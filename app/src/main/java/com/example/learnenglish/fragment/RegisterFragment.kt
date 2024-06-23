package com.example.learnenglish.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.learnenglish.activity.LogInActivity
import com.example.learnenglish.contract.RegisterContract
import com.example.learnenglish.databinding.FragmentRegisterBinding
import com.example.learnenglish.presenter.RegisterPresenter
import com.example.learnenglish.repository.DBHelperRepository
import java.lang.ref.WeakReference

class RegisterFragment : Fragment(), RegisterContract.View {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var fragmentManager: FragmentManager
    private lateinit var dialog: Dialog
    private lateinit var activityRef: WeakReference<LogInActivity>
    private lateinit var presenter: RegisterContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        init()
        getData()
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        val activity = activityRef.get()

        binding.imgBack.setOnClickListener {
            if (activity != null) {
                activity.binding.tvRegister.isEnabled = true
                activity.binding.btnlogin.isEnabled = true
                activity.binding.txtForgetPass.isEnabled = true
            }
            fragmentManager.popBackStack()
        }

        binding.btnSigUp.setOnClickListener {
            val email = binding.edtEmailRegister.text.toString()
            val passWord = binding.edtPasswordRegister.text.toString()
            val name = binding.edtNameRegister.text.toString()
            val reEnterPassword = binding.edtReEnterPassword.text.toString()
            presenter.onRegisterButtonClick(email, name, passWord, reEnterPassword)

            if (activity != null) {
                activity.binding.tvRegister.isEnabled = true
                activity.binding.btnlogin.isEnabled = true
                activity.binding.txtForgetPass.isEnabled = true
            }
        }
    }

    private fun getData(){
        val taskRepository = DBHelperRepository(requireContext())
        presenter = RegisterPresenter(requireActivity(), this, taskRepository)
    }

    private fun init() {
        fragmentManager = parentFragmentManager
        activityRef = WeakReference(requireActivity() as LogInActivity)

        val alertDialog = AlertDialog.Builder(context)
        val progressBar = ProgressBar(context)

        alertDialog.setView(progressBar)
        alertDialog.setTitle("Đang thực hiện !")
        alertDialog.setCancelable(false)
        dialog = alertDialog.create()
    }

    override fun showEmailError(message: String) {
        binding.edtEmailRegister.error = message
    }

    override fun showPasswordError(message: String) {
        binding.edtPasswordRegister.error = message
    }

    override fun showReEnterPasswordError(message: String) {
        binding.edtReEnterPassword.error = message
    }

    override fun showNameError(message: String) {
        binding.edtNameRegister.error = message
    }

    override fun showPasswordMismatchError(message: String, mk1: String, mk2: String) {
        binding.edtReEnterPassword.error = message
    }

    override fun onRegistrationSuccess(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        fragmentManager.popBackStack()
    }

    override fun onRegistrationFailure(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        dialog.show()
    }

    override fun hideLoading() {
        dialog.dismiss()
    }
}
