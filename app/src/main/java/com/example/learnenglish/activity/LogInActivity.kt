package com.example.learnenglish.activity

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.learnenglish.R
import com.example.learnenglish.contract.LogInContract
import com.example.learnenglish.databinding.ActivityLogInBinding
import com.example.learnenglish.fragment.RegisterFragment
import com.example.learnenglish.presenter.LogInPresenter
import com.example.learnenglish.presenter.RegisterPresenter
import com.example.learnenglish.repository.DBHelperRepository

class LogInActivity : AppCompatActivity(), LogInContract.View {

    lateinit var binding: ActivityLogInBinding
    private lateinit var dialog: Dialog
    private lateinit var presenter: LogInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        getData()
        setupUI()
    }

    private fun setupUI() {
        binding.btnlogin.setOnClickListener {
            val email = binding.edtemail.text.toString()
            val passWord = binding.edtpassword.text.toString()
            presenter.logIn(email, passWord)
        }
        binding.tvRegister.setOnClickListener {
            binding.btnlogin.isEnabled = false
            binding.txtForgetPass.isEnabled = false
            binding.tvRegister.isEnabled = false
            openRegisterFragment(RegisterFragment())
        }
    }
    private fun getData(){
        val taskRepository = DBHelperRepository(this)
        presenter = LogInPresenter(applicationContext, this@LogInActivity, taskRepository)
    }
    private fun init() {
        val alertDialog = AlertDialog.Builder(this)
        val progressBar = ProgressBar(this)

        alertDialog.setView(progressBar)
        alertDialog.setTitle("Đang thực hiện !")
        alertDialog.setCancelable(false)
        dialog = alertDialog.create()
    }

    private fun openRegisterFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.endter_from_right, R.anim.exit_to_right, R.anim.endter_from_right, R.anim.exit_to_right)
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun showLoading() {
        dialog.show()
    }

    override fun hideLoading() {
        dialog.dismiss()
    }

    override fun showMessageLogInFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}