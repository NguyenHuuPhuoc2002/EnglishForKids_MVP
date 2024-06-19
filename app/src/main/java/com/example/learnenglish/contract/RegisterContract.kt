package com.example.learnenglish.contract

interface RegisterContract {
    interface View{
        fun showEmailError(message: String)
        fun showPasswordError(message: String)
        fun showReEnterPasswordError(message: String)
        fun showNameError(message: String)
        fun showPasswordMismatchError(message: String, mk1: String, mk2: String)
        fun onRegistrationSuccess(message: String)
        fun onRegistrationFailure(message: String)
        fun showLoading()
        fun hideLoading()
    }
    interface Presenter{
        fun onRegisterButtonClick(email: String, name: String, reEnterPassword: String, password: String)
    }
}