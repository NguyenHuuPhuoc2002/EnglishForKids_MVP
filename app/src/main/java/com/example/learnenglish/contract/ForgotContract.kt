package com.example.learnenglish.contract

import android.widget.TextView

interface ForgotContract {
    interface View{
        fun showPassWordResetSuccess(message: String)
        fun showPassWordResetFail(message: String)
        fun showError(message: String)
        fun popBackStack()
        fun showLoading()
        fun hideLoading()
    }
    interface Presenter{
        fun resetPassWord(email: String)
    }
}