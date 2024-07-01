package com.example.learnenglish.presenter

import com.example.learnenglish.contract.SkillContractV2
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.fragment.SkillFragment
import com.example.learnenglish.model.SkillModel
import com.example.learnenglish.repository.DBHelperRepository

class SkillPresenterV2(private val view: SkillFragment): SkillContractV2.Presenter {
    override fun onStartActivity(skill: SkillModel, email: String) {
        if(skill.title == "Trắc Nghiệm"){
            view.showTopicActivity(skill.title, email)
        }else if(skill.title == "Từ Vựng"){
            view.showTopicActivity(skill.title, email)
        }else if(skill.title == "Luyện Nghe"){
            view.showTopicActivity(skill.title, email)
        }else if(skill.title == "Sắp Xếp Câu"){
            view.showTopicActivity(skill.title, email)
        }
    }

}