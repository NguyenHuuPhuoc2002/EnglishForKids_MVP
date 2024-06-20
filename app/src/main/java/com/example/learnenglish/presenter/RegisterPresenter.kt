package com.example.learnenglish.presenter

import android.content.Context
import com.example.learnenglish.contract.RegisterContract
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.fragment.RegisterFragment
import com.example.learnenglish.repository.DBHelperRepository

class RegisterPresenter(private val context: Context, private val view: RegisterFragment, private var db: DBHelperRepository)
    : RegisterContract.Presenter{
    override fun onRegisterButtonClick(
        email: String,
        name: String,
        reEnterPassword: String,
        password: String
    ) {
        db = DBHelperRepository(context)
        if (email.isEmpty() || email.isBlank() ||
            password.isEmpty() || password.isBlank() ||
            name.isEmpty() || name.isBlank() ||
            reEnterPassword.isEmpty() || reEnterPassword.isBlank() ||
            reEnterPassword.trim() != password.trim()) {

            if (email.isEmpty()) {
                view.showEmailError("Vui lòng nhập địa chỉ email!")
            }
            if (password.isEmpty()) {
                view.showPasswordError("Vui lòng nhập mật khẩu!")
            }
            if (reEnterPassword.isEmpty()) {
                view.showReEnterPasswordError("Vui lòng nhập lại mật khẩu!")
            }
            if (name.isEmpty()) {
                view.showNameError("Vui lòng nhập họ tên!")
            }
            if (reEnterPassword.trim() != password.trim()) {
                view.showPasswordMismatchError("Mật khẩu không khớp!", reEnterPassword, password)
            }
            view.hideLoading()
        } else {
            view.showLoading()
            db.registerDb(email, name, password, object : TaskCallback.TaskCallbackRegister{
                override fun showRegisterSuccess(message: String) {
                    view.onRegistrationSuccess(message)
                    view.hideLoading()
                }

                override fun showRegisterFail(message: String) {
                    view.onRegistrationFailure(message)
                    view.hideLoading()
                }

            })

        }

    }
}