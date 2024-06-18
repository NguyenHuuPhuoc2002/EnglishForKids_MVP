package com.example.learnenglish.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.learnenglish.activity.LogInActivity
import com.example.learnenglish.databinding.FragmentRegisterBinding
import com.example.learnenglish.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.ref.WeakReference

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var fragmentManager: FragmentManager
    private lateinit var activityRef: WeakReference<LogInActivity>
    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        init()
        dbRef = FirebaseDatabase.getInstance().getReference("Users")
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
            register(email, passWord, name, reEnterPassword)
        }

        return binding.root
    }

    private fun register(email: String, passWord: String, name: String, reEnterPassword: String) {
        if((email.isEmpty() && email.isBlank()) ||
            passWord.isEmpty() && passWord.isBlank() ||
            name.isEmpty() && name.isBlank() ||
            reEnterPassword.isEmpty() && reEnterPassword.isBlank() ||
            reEnterPassword.trim() != passWord.trim()){
            if (email.isEmpty()) {
                binding.edtEmailRegister.error = "Vui lòng nhập địa chỉ email!"
            }
            if (passWord.isEmpty()) {
                binding.edtPasswordRegister.error = "Vui lòng nhập mật khẩu!"
            }
            if (reEnterPassword.isEmpty()) {
                binding.edtReEnterPassword.error = "Vui lòng nhập lại mật khẩu!"
            }
            if (name.isEmpty()) {
                binding.edtNameRegister.error = "Vui lòng nhập họ tên!"
            }
            if (reEnterPassword.trim() != passWord.trim()) {
                binding.edtReEnterPassword.error = "Mật khẩu không khớp!"
            }
        }else{
            val id = dbRef.push().key
            firebaseAuth.createUserWithEmailAndPassword(email, passWord)
                .addOnCompleteListener(requireActivity()){ task ->
                    if(task.isSuccessful){
                        view?.postDelayed({
                            val user = UserModel(id, name, email, 0, 0)
                            dbRef.child(id!!).setValue(user)
                                .addOnSuccessListener {
                                    fragmentManager.popBackStack()
                                    Toast.makeText(context, "Đăng kí thành công!", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Đăng kí không thành công!", Toast.LENGTH_SHORT).show()
                                }
                        }, 100)
                    }else{
                        val errorMessage = task.exception?.message
                        Toast.makeText(context, "Lỗi: $errorMessage", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }


    private fun init() {
        fragmentManager = parentFragmentManager
        activityRef = WeakReference(requireActivity() as LogInActivity)
        firebaseAuth = FirebaseAuth.getInstance()

    }


}