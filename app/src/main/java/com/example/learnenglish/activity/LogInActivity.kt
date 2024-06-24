package com.example.learnenglish.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.learnenglish.R
import com.example.learnenglish.contract.LogInContract
import com.example.learnenglish.databinding.ActivityLogInBinding
import com.example.learnenglish.fragment.ForgotFragment
import com.example.learnenglish.fragment.RegisterFragment
import com.example.learnenglish.presenter.LogInPresenter
import com.example.learnenglish.repository.DBHelperRepository

class LogInActivity : AppCompatActivity(), LogInContract.View {

    lateinit var binding: ActivityLogInBinding
    private lateinit var dialog: Dialog
    private lateinit var presenter: LogInPresenter
    private lateinit var email: String
    private lateinit var passWord: String
    private lateinit var preferences: SharedPreferences
    private lateinit var checkbox: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        getData()
        setupUI()
    }

    override fun onResume() {
        super.onResume()
        presenter.restoreData(checkbox)
    }


    private fun setupUI() {
        binding.btnlogin.setOnClickListener {
            email = binding.edtemail.text.toString()
            passWord = binding.edtpassword.text.toString()
            presenter.logIn(email, passWord)
        }
        binding.tvRegister.setOnClickListener {
            binding.btnlogin.isEnabled = false
            binding.txtForgetPass.isEnabled = false
            binding.tvRegister.isEnabled = false
            openFragment(RegisterFragment())
        }
        binding.cbRember.setOnCheckedChangeListener { _, isChecked ->
            presenter.saveDataInf(isChecked)
        }
        binding.txtForgetPass.setOnClickListener {
            binding.btnlogin.isEnabled = false
            binding.txtForgetPass.isEnabled = false
            binding.tvRegister.isEnabled = false
            openFragment(ForgotFragment())
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

        preferences = getSharedPreferences("checkbox", MODE_PRIVATE)
        checkbox = preferences.getString("remember", "").toString()
    }

    private fun openFragment(fragment: Fragment) {
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

    override fun trueDataHandle() {
        val edtEmail = binding.edtemail.text.toString()
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString("remember", "true")
        editor.putString("email", edtEmail)
        editor.apply()
    }

    override fun falseDataHandle() {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString("remember", "false")
        editor.remove("email")
        editor.apply()
    }


    override fun intenDataTransfer(message: String,email: String) {
        val intent = Intent(this, SkillActivity::class.java)
        intent.putExtra("Login", message)
        intent.putExtra("email_", email.replace(".", ""))
        intent.putExtra("email", email)
        startActivity(intent)
    }

    override fun showMessageLogInFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showDataAfterRestore(checkBox: String) {
        val email: String? = preferences.getString("email", "")
        val intent = Intent(this@LogInActivity, SkillActivity::class.java)
        intent.putExtra("email", email)
        startActivity(intent)
    }
}