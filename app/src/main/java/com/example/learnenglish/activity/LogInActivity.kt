package com.example.learnenglish.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.learnenglish.R
import com.example.learnenglish.databinding.ActivityLogInBinding
import com.example.learnenglish.fragment.RegisterFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LogInActivity : AppCompatActivity() {

    lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnlogin.setOnClickListener {
            logIn()
        }
        binding.tvRegister.setOnClickListener {
            binding.btnlogin.isEnabled = false
            binding.txtForgetPass.isEnabled = false
            binding.tvRegister.isEnabled = false
            openRegisterFragment(RegisterFragment())
        }

    }

    private fun openRegisterFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(R.anim.endter_from_right, R.anim.exit_to_right, R.anim.endter_from_right, R.anim.exit_to_right)
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun logIn() {

    }
}