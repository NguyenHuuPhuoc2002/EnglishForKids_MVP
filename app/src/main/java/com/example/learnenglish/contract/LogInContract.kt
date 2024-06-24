package com.example.learnenglish.contract

interface LogInContract {
    interface View{
        fun showLoading()
        fun hideLoading()
        fun trueDataHandle()
        fun falseDataHandle()

        fun intenDataTransfer(message: String ,email: String)
        fun showMessageLogInFailure(message: String)
        fun showDataAfterRestore(checkBox: String)
    }
    interface Presenter{
        fun logIn(email: String, passWord: String)
        fun saveDataInf(isChecked: Boolean)
        fun restoreData(checkBox: String)
    }
}