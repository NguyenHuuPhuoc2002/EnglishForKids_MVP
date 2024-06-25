package com.example.learnenglish.contract

import android.os.Message
import com.example.learnenglish.model.SkillModel

interface SkillContract {
    interface View{
        fun showTopicActivity(title: String, email: String)
        fun showErrorMessage(message: String)
    }
    interface Presenter{
        fun onStartActivity(skill: SkillModel, email: String)
        fun getUser(email: String, callback: TaskCallback.TaskCallbackUser2)
    }
}