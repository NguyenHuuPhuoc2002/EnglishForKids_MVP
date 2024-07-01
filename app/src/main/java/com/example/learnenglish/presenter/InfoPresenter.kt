package com.example.learnenglish.presenter

import com.example.learnenglish.contract.InfoContract
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.fragment.InfoFragment
import com.example.learnenglish.model.UserModel
import com.example.learnenglish.repository.DBHelperRepository

class InfoPresenter(private val view: InfoFragment, private val db: DBHelperRepository): InfoContract.Presenter {
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
                    view.showErrorMessage("Không tìm thấy người dùng với email $email")
                }
            }

        })
    }

    override fun update(id: String, nameUser: String) {
        if(nameUser.isNotEmpty()){
            db.upDateNameUser(id, nameUser, object : TaskCallback.TaskCallbackForgotInfo{
                override fun onSuccess(message: String) {
                    view.showSuccesMessage(message)
                }

                override fun onFail(message: String) {
                    view.showFailMessage(message)
                }

            })
        }else{
            view.showTextBlankMessage("Không được để trống!")
        }

    }
}