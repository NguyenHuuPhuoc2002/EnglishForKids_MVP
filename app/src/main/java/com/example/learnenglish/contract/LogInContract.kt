package com.example.learnenglish.contract

interface LogInContract {
    interface View{
        fun showLoading()
        fun hideLoading()
        fun showMessageLogInFailure(message: String)
    }
    interface Presenter{
        fun logIn(email: String, passWord: String)
    }
}