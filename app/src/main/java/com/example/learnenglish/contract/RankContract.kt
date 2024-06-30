package com.example.learnenglish.contract

interface RankContract {
    interface View{

    }
    interface Present{
        fun getUser(email: String, callback: TaskCallback.TaskCallbackUserRankList)
    }
}