package com.example.learnenglish.presenter

import android.util.Log
import com.example.learnenglish.contract.RankContract
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.model.UserModel
import com.example.learnenglish.repository.DBHelperRepository

class RankPresenter(private var db: DBHelperRepository): RankContract.Present {

    override fun getUser(email: String, callback: TaskCallback.TaskCallbackUserRankList) {
        val mListUser = ArrayList<UserModel>()
        db.getItemUser(object : TaskCallback.TaskCallbackUser{
            override fun onListUserLoaded(listUser: ArrayList<UserModel>) {
                for(user in listUser){
                    mListUser.add(user)
                }
                callback.onListUserLoadedRank(mListUser)
            }
        })
    }
}