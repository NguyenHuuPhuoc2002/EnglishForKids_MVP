package com.example.learnenglish.presenter

import android.content.Intent
import com.example.learnenglish.activity.SkillActivity
import com.example.learnenglish.activity.TopicActivity
import com.example.learnenglish.contract.SkillContract
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.fragment.SkillFragment
import com.example.learnenglish.model.SkillModel
import com.example.learnenglish.model.UserModel
import com.example.learnenglish.repository.DBHelperRepository

class SkillPresenter(private val viewFragment: SkillFragment, private val viewActivity: SkillActivity, private val email: String, private val db: DBHelperRepository): SkillContract.Presenter {
    override fun onStartActivity(skill: SkillModel, email: String) {
        if(skill.title == "Trắc Nghiệm"){
            viewFragment.showTopicActivity(skill.title, email)
        }else if(skill.title == "Từ Vựng"){
            viewFragment.showTopicActivity(skill.title, email)
        }else if(skill.title == "Luyện Nghe"){
            viewFragment.showTopicActivity(skill.title, email)
        }else if(skill.title == "Sắp Xếp Câu"){
            viewFragment.showTopicActivity(skill.title, email)
        }
    }

    override fun getUser(email: String, callback: TaskCallback.TaskCallbackUserRank) {
        db.getItemUser(object : TaskCallback.TaskCallbackUser{
            override fun onListUserLoaded(listUser: ArrayList<UserModel>) {
                var mUser: UserModel? = null
                for (user in listUser){
                    if(user.email.toString() == email){
                        mUser = user
                        break
                    }
                }
                val sortedList = listUser.sortedWith(Comparator { a, b -> a.point!!.compareTo(b.point!!) })
                val newList = sortedList.reversed()
                if(mUser != null){
                    for(i in 0 until newList.size - 1){
                        if(newList[i].email == email){
                            val rank = i + 1
                            callback.onListUserLoaded(mUser, rank)
                            return
                        }
                    }
                }else{
                    viewActivity.showErrorMessage("Không tìm thấy người dùng với email $email")
                }
            }

        })
    }
}