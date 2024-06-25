package com.example.learnenglish.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learnenglish.R
import com.example.learnenglish.adapter.SkillAdapter
import com.example.learnenglish.contract.QuizzesContract
import com.example.learnenglish.contract.SkillContract
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.databinding.ActivitySkillBinding
import com.example.learnenglish.model.SkillModel
import com.example.learnenglish.model.UserModel
import com.example.learnenglish.presenter.LogInPresenter
import com.example.learnenglish.presenter.SkillPresenter
import com.example.learnenglish.repository.DBHelperRepository
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class SkillActivity : AppCompatActivity(), SkillContract.View{
    private lateinit var binding: ActivitySkillBinding
    private lateinit var mListHome: ArrayList<SkillModel>
    private lateinit var presenter: SkillContract.Presenter
    private lateinit var email: String
    private var nameUser: String? = null
    private lateinit var dialog: Dialog
    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySkillBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        addDataHome()
        setAdapterHome()
        getDataFromIntent()
        setupPresenterAndFetchUser()
        Log.d("email_skill", email)
        setupUI()
        navigation()
    }
    private fun setupPresenterAndFetchUser(){
        val taskRepository = DBHelperRepository(this)
        presenter = SkillPresenter(this@SkillActivity, email, taskRepository)
        presenter.getUser(email, object : TaskCallback.TaskCallbackUser2{
            override fun onListUserLoaded(user: UserModel) {
                nameUser = user.name.toString()
                navHeadGetEmailUser(nameUser!!)
            }

        })
    }
    private fun init() {
        val alertDialog = AlertDialog.Builder(this)
        val progressBar = ProgressBar(this)

        alertDialog.setView(progressBar)
        alertDialog.setTitle("Đang thực hiện !")
        alertDialog.setCancelable(false)
        dialog = alertDialog.create()
    }
    private fun navHeadGetEmailUser(nameUser: String) {
        val navigationView = findViewById<NavigationView>(R.id.navigation_drawer)
        val headerView = navigationView.getHeaderView(0)
        val emailTextView = headerView.findViewById<TextView>(R.id.tv_name)
        emailTextView.text = nameUser
    }
    private fun setupUI() {
        val drawerLayout = binding.drawLayout
        val navView = binding.navigationDrawer
        binding.imgNav.setOnClickListener {
            drawerLayout.openDrawer(navView)
        }
    }
    private fun navigation(){
        binding.navigationDrawer.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_rank -> {
                    Toast.makeText(this, "Rank", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_setting -> {
                    Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_admin -> {
                    Toast.makeText(this, "Admin", Toast.LENGTH_SHORT).show()
                }

                R.id.nav_logout -> {
                    dialog.show()
                    val handler = Handler(Looper.getMainLooper())
                    handler.postDelayed({
                        val preferences : SharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE)
                        val editor :SharedPreferences.Editor = preferences.edit()
                        editor.putString("remember", "false")
                        editor.apply()
                        val intent = Intent(this, LogInActivity::class.java)
                        startActivity(intent)
                    }, 1200)
                }
            }
            binding.drawLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
    private fun openFragment(fragment: Fragment){
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
    private fun getDataFromIntent(){
        val intent = intent
        email = intent.getStringExtra("email").toString()
    }
    private fun setAdapterHome(){
        val adapter = SkillAdapter(mListHome, object : QuizzesContract.OnClickListener{
            override fun onClickListenerItemHome(pos: Int) {
                presenter.onStartActivity(mListHome[pos], email)
            }
        })
        binding.rcvHome.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        binding.rcvHome.adapter = adapter

    }

    private fun addDataHome(){
        mListHome = arrayListOf()
        mListHome.add(SkillModel(R.drawable.img_tracnghiem, "Trắc Nghiệm"))
        mListHome.add(SkillModel(R.drawable.img_tracnghiem, "Từ Vựng"))
        mListHome.add(SkillModel(R.drawable.img_tracnghiem, "Luyện Nghe"))
        mListHome.add(SkillModel(R.drawable.img_tracnghiem, "Sắp Xếp Câu"))
    }

    override fun showTopicActivity(title: String, email: String) {
        val intent = Intent(this@SkillActivity, TopicActivity::class.java)
        intent.putExtra("title_skill", title)
        intent.putExtra("email", email)
        startActivity(intent)
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if(binding.drawLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawLayout.closeDrawer(GravityCompat.START)
        }else{
            super.getOnBackPressedDispatcher().onBackPressed()
        }
    }

}