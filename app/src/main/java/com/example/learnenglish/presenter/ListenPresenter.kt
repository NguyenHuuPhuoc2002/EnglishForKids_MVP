package com.example.learnenglish.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import com.example.learnenglish.R
import com.example.learnenglish.activity.ListenActivity
import com.example.learnenglish.activity.VocabularyActivity
import com.example.learnenglish.contract.ListenContract
import com.example.learnenglish.contract.VocabularyContract
import com.example.learnenglish.model.ListenAnswerModel
import com.example.learnenglish.model.ListenQuestionModel
import com.example.learnenglish.model.TopicModel
import com.example.learnenglish.model.VocabularyAnsModel
import com.example.learnenglish.model.VocabularyQuesModel
import com.example.learnenglish.repository.DBHelperRepository
import com.example.learnenglish_demo.AnswerModel
import com.example.learnenglish_demo.QuestionModel


class ListenPresenter(private val context: Context, private val view: ListenActivity, private var db: DBHelperRepository) : ListenContract.Presenter {
    companion object {
        val handler = Handler(Looper.getMainLooper())
    }

    override fun getItemsListenQuestion(): ArrayList<ListenQuestionModel> {
        db = DBHelperRepository(context)
        db.openDatabase()
        val mList: ArrayList<ListenQuestionModel> = ArrayList()
        db.getItemsQuestionsListen(object : DBHelperRepository.TaskCallback {
            override fun onListQuestionLoaded(mListQuestion: ArrayList<QuestionModel>?) {
                TODO("Not yet implemented")
            }

            override fun onListAnswerLoaded(mListAnswer: ArrayList<AnswerModel>?) {
                TODO("Not yet implemented")
            }

            override fun onListTopicLoaded(mListTopic: ArrayList<TopicModel>?) {
                TODO("Not yet implemented")
            }

            override fun onListVocabularyQuestionLoaded(mListVocQues: ArrayList<VocabularyQuesModel>?) {
                TODO("Not yet implemented")
            }

            override fun onListVocabularyAnswerLoaded(mListVocAns: ArrayList<VocabularyAnsModel>?) {
                TODO("Not yet implemented")
            }

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
        db.getItemsAnswersListen(object : DBHelperRepository.TaskCallback{
            override fun onListQuestionLoaded(mListQuestion: ArrayList<QuestionModel>?) {
                TODO("Not yet implemented")
            }

            override fun onListAnswerLoaded(mListAnswer: ArrayList<AnswerModel>?) {
                TODO("Not yet implemented")
            }

            override fun onListTopicLoaded(mListTopic: ArrayList<TopicModel>?) {
                TODO("Not yet implemented")
            }

            override fun onListVocabularyQuestionLoaded(mListVocQues: ArrayList<VocabularyQuesModel>?) {
                TODO("Not yet implemented")
            }

            override fun onListVocabularyAnswerLoaded(mListVocAns: ArrayList<VocabularyAnsModel>?) {
                TODO("Not yet implemented")
            }

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

    override fun checkAnswer(textview: TextView, mListQues: ArrayList<ListenQuestionModel>,
                                mListAns: ArrayList<ListenAnswerModel>, currentPos: Int
    ) {
        val newTextView = textview.text.toString().trim().toLowerCase()
        val newIsCorrect = mListAns[currentPos].isCorrect.trim().toLowerCase()
        view.showResult(newIsCorrect == newTextView, textview)
        if(textview.text.isNotEmpty()){
            if(newIsCorrect == newTextView){
//                nextQuestion(mListQues, mListAns, currentPos)
            }else{
                VocabularyPresenter.handler.postDelayed({
                    view.showActivityFinished(mListQues.size, currentPos, 0)
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
                view.showActivityFinished(mListQues.size, newPos, 0)
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
