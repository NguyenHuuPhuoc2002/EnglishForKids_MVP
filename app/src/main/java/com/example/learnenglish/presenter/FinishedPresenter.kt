package com.example.learnenglish.presenter

import android.content.Context
import com.example.learnenglish.contract.FinishedContract
import com.example.learnenglish.repository.DBHelperRepository

class FinishedPresenter(private val context: Context, private var db: DBHelperRepository): FinishedContract.Presenter {
    override fun updatePoint(id: String, point: Int) {
        db = DBHelperRepository(context)
        db.openDatabase()
        db.upDatePoint(id, point)
    }
}