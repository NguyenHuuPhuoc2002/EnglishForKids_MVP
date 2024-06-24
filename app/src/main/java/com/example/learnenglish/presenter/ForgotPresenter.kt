package com.example.learnenglish.presenter

import android.content.Context
import android.widget.TextView
import com.example.learnenglish.contract.ForgotContract
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.fragment.ForgotFragment
import com.example.learnenglish.repository.DBHelperRepository

class ForgotPresenter(private val context: Context, private val  view: ForgotFragment, private var db: DBHelperRepository )
    : ForgotContract.Presenter{
    override fun resetPassWord(email: String) {
        db = DBHelperRepository(context)
        if(email.isEmpty()){
            view.showError("Vui lòng nhập địa chỉ email")
        }else{
            view.showLoading()
            db.upDatePassWord(email, object : TaskCallback.TaskCallbackForgotPassWord{
                override fun onSuccess(message: String) {
                    view.hideLoading()
                    view.showPassWordResetSuccess(message)
                    view.popBackStack()
                }

                override fun onFail(message: String) {
                    view.hideLoading()
                    view.showPassWordResetFail(message)
                }

            })
        }

    }
}