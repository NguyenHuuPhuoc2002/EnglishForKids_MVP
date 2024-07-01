package com.example.learnenglish.presenter

import android.content.Context
import com.example.learnenglish.activity.LogInActivity
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
                    view.intenDataTransfer(message, email)
                    view.hideLoading()
                }

                override fun onLogInFail(message: String) {
                    view.showMessageLogInFailure(message)
                    view.hideLoading()
                }

            })
        }
    }

    override fun saveDataInf(isChecked: Boolean) {
        if (isChecked) {
            view.trueDataHandle()
        } else{
            view.falseDataHandle()
        }
    }

    override fun restoreData(checkBox: String) {
        if (checkBox == "true") {
            view.showDataAfterRestore(checkBox)
        }
    }
}