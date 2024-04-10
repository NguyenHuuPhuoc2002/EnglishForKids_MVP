package com.example.learnenglish.contract

import com.example.learnenglish.model.TopicModel

interface TopicContract {
    interface View{
        fun showTopic(mListTopic: ArrayList<TopicModel>)
        fun showErrorMessage(message: String)
    }
    interface Presenter {
        fun getItemsTopic(): ArrayList<TopicModel>
    }
}