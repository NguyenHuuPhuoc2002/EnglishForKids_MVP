package com.example.learnenglish.presenter

import android.content.Intent
import com.example.learnenglish.activity.SkillActivity
import com.example.learnenglish.activity.TopicActivity
import com.example.learnenglish.contract.SkillContract
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.model.SkillModel
import com.example.learnenglish.model.UserModel
import com.example.learnenglish.repository.DBHelperRepository

class SkillPresenter(private val view: SkillActivity, private val email: String, private val db: DBHelperRepository): SkillContract.Presenter {
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

    override fun getUser(email: String, callback: TaskCallback.TaskCallbackUser2) {
        db.getItemUser(object : TaskCallback.TaskCallbackUser{
            override fun onListUserLoaded(listUser: ArrayList<UserModel>) {
                var mUser: UserModel? = null
                for (user in listUser){
                    if(user.email.toString() == email){
                        mUser = user
                        break
                    }
                }
                if(mUser != null){
                    callback.onListUserLoaded(mUser)
                }else{
                    view.showErrorMessage("Không tìm thấy người dùng với email $email")
                }
            }

        })
    }
}