package com.example.learnenglish.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import com.example.learnenglish.activity.ListenActivity
import com.example.learnenglish.contract.ListenContract
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.model.ListenAnswerModel
import com.example.learnenglish.model.ListenQuestionModel
import com.example.learnenglish.model.UserModel
import com.example.learnenglish.repository.DBHelperRepository
import kotlin.properties.Delegates


class ListenPresenter(private val context: Context, private val view: ListenActivity, private var db: DBHelperRepository) : ListenContract.Presenter {
    companion object {
        val handler = Handler(Looper.getMainLooper())
    }
    private var newPoint by Delegates.notNull<Int>()
    override fun getItemsListenQuestion(): ArrayList<ListenQuestionModel> {
        db = DBHelperRepository(context)
        db.openDatabase()
        val mList: ArrayList<ListenQuestionModel> = ArrayList()
        db.getItemsQuestionsListen(object : TaskCallback.TaskCallbackListen {

            override fun onListListenQuestionLoaded(mListLisQues: ArrayList<ListenQuestionModel>?) {
                if (mListLisQues != null) {
                    for (i in 0 until mListLisQues.size) {
                        if (mListLisQues[i].topicID == view.id.toString()) {
                            mList.add(mListLisQues[i])
                            view.showListenQuestion(mList)
                        }
                    }
                } else {
                    view.showErrorMessage("Không tải được dữ liệu !")
                }
            }

            override fun onListListenAnswerLoaded(mListLisAns: ArrayList<ListenAnswerModel>?) {
                TODO("Not yet implemented")
            }
        })
        return mList
    }

    override fun getItemsListenAnswer(): ArrayList<ListenAnswerModel> {
        val mList: ArrayList<ListenAnswerModel> = ArrayList()
        db.getItemsAnswersListen(object : TaskCallback.TaskCallbackListen{
            override fun onListListenQuestionLoaded(mListLisQues: ArrayList<ListenQuestionModel>?) {
                TODO("Not yet implemented")
            }

            override fun onListListenAnswerLoaded(mListLisAns: ArrayList<ListenAnswerModel>?) {
                if (mListLisAns != null) {
                    for (i in 0 until mListLisAns.size) {
                        if (mListLisAns[i].topicID == view.id.toString()) {
                            mList.add(mListLisAns[i])
                            view.showListenAnswer(mList)
                        }
                    }
                } else {
                    view.showErrorMessage("Không tải được dữ liệu !")
                }
            }

        })
        return mList
    }

    override fun getUser(email: String, callback: TaskCallback.TaskCallbackUser2) {
        db.getItemUser(object : TaskCallback.TaskCallbackUser{
            override fun onListUserLoaded(listUser: ArrayList<UserModel>) {
                var mUser: UserModel? = null
                for(user in listUser){
                    if(user.email.toString() == email){
                        mUser = user
                        break
                    }
                }
                if(mUser != null){
                    callback.onListUserLoaded(mUser)
                }else{
                    view.showErrorMessage("Không tìm thấy người dùng với email $email\"")
                }
            }
        })
    }

    override fun updatePoint(id: String, point: Int) {
        db.upDatePoint(id, point)
    }

    override fun checkAnswer(textview: TextView, mListQues: ArrayList<ListenQuestionModel>,
                                mListAns: ArrayList<ListenAnswerModel>, currentPos: Int, point: Int
    ) {
        newPoint = point
        val newTextView = textview.text.toString().trim().toLowerCase()
        val newIsCorrect = mListAns[currentPos].isCorrect.trim().toLowerCase()
        view.showResult(newIsCorrect == newTextView, textview)
        if(textview.text.isNotEmpty()){
            if(newIsCorrect == newTextView){
                newPoint += 5
                view.showPoint(newPoint)
//                nextQuestion(mListQues, mListAns, currentPos)
            }else{
                VocabularyPresenter.handler.postDelayed({
                    view.showActivityFinished(mListQues.size, currentPos, newPoint)
                },1000)
            }
        }
    }
     @SuppressLint("SetTextI18n")
     fun nextQuestion(mListQues: ArrayList<ListenQuestionModel>, mListAns: ArrayList<ListenAnswerModel>,
                      newCurrentPos: Int) {
        if(newCurrentPos == mListQues.size - 1){
            val newPos = newCurrentPos + 1
            VocabularyPresenter.handler.postDelayed({
                view.showActivityFinished(mListQues.size, newPos, newPoint)
            },1000)
        }else{
            val incrementedPos = newCurrentPos + 1
            val newNumQuesCurent = incrementedPos + 1
            view.showNextQuestion(mListQues, mListAns, incrementedPos)
            view.setData(incrementedPos)
            view.showNumQuesCurent(newNumQuesCurent)
        }
    }

}
