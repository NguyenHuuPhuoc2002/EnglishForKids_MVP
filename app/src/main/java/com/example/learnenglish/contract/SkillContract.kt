package com.example.learnenglish.contract

import com.example.learnenglish.model.SkillModel

interface SkillContract {
    interface View{
        fun showTopicActivity(title: String)
    }
    interface Presenter{
        fun onStartActivity(skill: SkillModel)
    }
}