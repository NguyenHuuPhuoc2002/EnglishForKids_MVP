package com.example.learnenglish.contract

import android.os.Message
import com.example.learnenglish.model.SkillModel

interface SkillContractV2 {
    interface View{
        fun showTopicActivity(title: String, email: String)
        fun showErrorMessage(message: String)
    }
    interface Presenter{
        fun onStartActivity(skill: SkillModel, email: String)
    }
}