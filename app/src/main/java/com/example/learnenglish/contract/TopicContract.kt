package com.example.learnenglish.contract

import android.app.Activity
import com.example.learnenglish.model.SkillModel
import com.example.learnenglish.model.TopicModel

interface TopicContract {
    interface View{
        fun showTopic(mListTopic: ArrayList<TopicModel>)
        fun showErrorMessage(message: String)
        fun showActivity(email: String, id: String, str: String)
    }
    interface Presenter {
        fun getItemsTopic(): ArrayList<TopicModel>
        fun onStartActivity(str: String, email: String, id: String)
    }
}