package com.example.learnenglish.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import com.example.learnenglish.activity.VocabularyActivity
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.contract.VocabularyContract
import com.example.learnenglish.model.UserModel
import com.example.learnenglish.model.VocabularyAnsModel
import com.example.learnenglish.model.VocabularyQuesModel
import com.example.learnenglish.repository.DBHelperRepository
import kotlin.properties.Delegates


class VocabularyPresenter(private val context: Context, private val view: VocabularyActivity, private var db: DBHelperRepository) : VocabularyContract.Presenter {
    companion object {
        val handler = Handler(Looper.getMainLooper())
    }
    private var newPoint by Delegates.notNull<Int>()
    override fun getItemsVocabularyQuestion(): ArrayList<VocabularyQuesModel> {
        db = DBHelperRepository(context)
        db.openDatabase()
        val mList: ArrayList<VocabularyQuesModel> = ArrayList()
        db.getItemsQuestionVocabulary(object : TaskCallback.TaskCallbackVocabulary{
            override fun onListVocabularyQuestionLoaded(mListVocQues: ArrayList<VocabularyQuesModel>?) {
                if(mListVocQues != null){
                    for (i in 0 until mListVocQues.size) {
                        if(mListVocQues[i].topicID == view.id.toString()){
                            mList.add(mListVocQues[i])
                            view.showVocabularyQuestion(mList)
                        }
                    }
                }else {
                    view.showErrorMessage("Không tải được dữ liệu !")
                }
            }

            override fun onListVocabularyAnswerLoaded(mListVocAns: ArrayList<VocabularyAnsModel>?) {
                TODO("Not yet implemented")
            }
        })
        return mList
    }

    override fun getItemsVocabularyAnswer(): ArrayList<VocabularyAnsModel> {
        val mList: ArrayList<VocabularyAnsModel> = ArrayList()
        db.getItemsAnswerVocabulary(object : TaskCallback.TaskCallbackVocabulary{
            override fun onListVocabularyQuestionLoaded(mListVocQues: ArrayList<VocabularyQuesModel>?) {
            }

            override fun onListVocabularyAnswerLoaded(mListVocAns: ArrayList<VocabularyAnsModel>?) {
                if(mListVocAns != null){
                    for (i in 0 until mListVocAns.size) {
                        if(mListVocAns[i].topicID == view.id.toString()){
                            mList.add(mListVocAns[i])
                            view.showVocabularyAnswer(mList)
                        }
                    }
                }else {
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

    override fun updatePoint(id: String, point: Int){
        db.upDatePoint(id, point)
    }


    override fun checkAnswer(textview: TextView, mListQues: ArrayList<VocabularyQuesModel>,
                                mListAns: ArrayList<VocabularyAnsModel>, currentPos: Int, point: Int) {
        newPoint = point
        view.showResult(mListAns[currentPos].isCorrect == textview.text, textview)
        if(textview.text.isNotEmpty()){
            if(mListAns[currentPos].isCorrect == textview.text){
                newPoint += 5
                view.showPoint(newPoint)
                nextQuestion(mListQues, mListAns, currentPos)
            }else{
                handler.postDelayed({
                    view.showActivityFinished(mListQues.size, currentPos, newPoint)
                },1000)
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun nextQuestion(mListQues: ArrayList<VocabularyQuesModel>, mListAns: ArrayList<VocabularyAnsModel>,
                             newCurrentPos: Int) {
        if(newCurrentPos == mListQues.size - 1){
            val newPos = newCurrentPos + 1
            handler.postDelayed({
                view.showActivityFinished(mListQues.size, newPos, newPoint)
            },1000)
        }else{
            val incrementedPos = newCurrentPos + 1
            val newNumQuesCurent = incrementedPos + 1
            handler.postDelayed({
                view.showNextQuestion(mListQues, mListAns, incrementedPos)
            },1000)
            handler.postDelayed({
                view.showNumQuesCurent(newNumQuesCurent)
            }, 1000)
        }
    }
}
