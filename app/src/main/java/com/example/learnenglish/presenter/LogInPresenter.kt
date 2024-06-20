package com.example.learnenglish.presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.learnenglish.activity.LogInActivity
import com.example.learnenglish.activity.SkillActivity
import com.example.learnenglish.contract.LogInContract
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.repository.DBHelperRepository

class LogInPresenter(private val context: Context, private val view: LogInActivity, private var db: DBHelperRepository)
    : LogInContract.Presenter{
    override fun logIn(email: String, passWord: String) {
        db = DBHelperRepository(context)
        db.openDatabase()
        if(email.isNotEmpty() && passWord.isNotEmpty()){
            view.showLoading()
            db.getLogIn(email, passWord, object : TaskCallback.TaskCallbackLogin{
                override fun onLogInSuccess(message: String) {
                    val intent = Intent(context, SkillActivity::class.java)
                    intent.putExtra("Login", message)
                    intent.putExtra("email", email.replace(".", ""))
                    intent.putExtra("emailAcountTitle", email)
                    context.startActivity(intent)
                    view.hideLoading()
                }

                override fun onLogInFail(message: String) {
                    view.showMessageLogInFailure(message)
                    view.hideLoading()
                }

            })
        }
    }
}