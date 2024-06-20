package com.example.learnenglish.presenter

import android.content.Intent
import com.example.learnenglish.activity.SkillActivity
import com.example.learnenglish.activity.TopicActivity
import com.example.learnenglish.contract.SkillContract
import com.example.learnenglish.model.SkillModel

class SkillPresenter(private val view: SkillActivity): SkillContract.Presenter {
    override fun onStartActivity(skill: SkillModel) {
        if(skill.title == "Trắc Nghiệm"){
            view.showTopicActivity(skill.title)
        }else if(skill.title == "Từ Vựng"){
            view.showTopicActivity(skill.title)
        }else if(skill.title == "Luyện Nghe"){
            view.showTopicActivity(skill.title)
        }else if(skill.title == "Sắp Xếp Câu"){
            view.showTopicActivity(skill.title)
        }
    }
}