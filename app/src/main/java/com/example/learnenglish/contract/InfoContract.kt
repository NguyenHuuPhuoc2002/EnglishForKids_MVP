package com.example.learnenglish.contract

interface InfoContract {
    interface View{
        fun showErrorMessage(message: String)
        fun showSuccesMessage(message: String)
        fun showFailMessage(message: String)
        fun showTextBlankMessage(message: String)
    }

    interface Presenter{
        fun getUser(email: String, callback: TaskCallback.TaskCallbackUserRank)
        fun update(id: String, nameUser: String)
    }
}