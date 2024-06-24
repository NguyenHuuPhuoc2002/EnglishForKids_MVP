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
import com.example.learnenglish.R
import com.example.learnenglish.activity.LogInActivity
import com.example.learnenglish.contract.ForgotContract
import com.example.learnenglish.databinding.FragmentForgotBinding
import com.example.learnenglish.presenter.ForgotPresenter
import com.example.learnenglish.repository.DBHelperRepository
import java.lang.ref.WeakReference

class ForgotFragment : Fragment(), ForgotContract.View{
    private lateinit var activityRef: WeakReference<LogInActivity>
    private lateinit var dialog: Dialog
    private lateinit var fragmentManager: FragmentManager
    private lateinit var presenter: ForgotContract.Presenter
    private lateinit var binding: FragmentForgotBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotBinding.inflate(inflater, container, false)
        init()
        setUI()
        return binding.root
    }
    private fun setUI() {
        binding.btnReset.setOnClickListener {
            val email = binding.edtEmailForgot.text.toString()
            presenter.resetPassWord(email)
            binding.edtEmailForgot.setText("")
        }
        binding.btnback.setOnClickListener {
            val activity = activityRef.get()
            if (activity != null) {
                activity.binding.tvRegister.isEnabled = true
                activity.binding.btnlogin.isEnabled = true
                activity.binding.txtForgetPass.isEnabled = true
            }
            fragmentManager.popBackStack()
        }
    }

    private fun init() {
        val alertDialog = AlertDialog.Builder(requireActivity())
        val progressBar = ProgressBar(requireActivity())

        alertDialog.setView(progressBar)
        alertDialog.setTitle("Đang thực hiện !")
        alertDialog.setCancelable(false)
        dialog = alertDialog.create()

        fragmentManager = parentFragmentManager
        activityRef = WeakReference(requireActivity() as LogInActivity)

        val taskRepository = DBHelperRepository(requireActivity())
        presenter = ForgotPresenter(requireActivity(), this, taskRepository)
    }

    override fun showPassWordResetSuccess(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showPassWordResetFail(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun popBackStack() {
        val activity = activityRef.get()
        if (activity != null) {
            activity.binding.tvRegister.isEnabled = true
            activity.binding.btnlogin.isEnabled = true
            activity.binding.txtForgetPass.isEnabled = true
        }
        fragmentManager.popBackStack()
    }

    override fun showLoading() {
        dialog.show()
    }

    override fun hideLoading() {
        dialog.dismiss()
    }


}