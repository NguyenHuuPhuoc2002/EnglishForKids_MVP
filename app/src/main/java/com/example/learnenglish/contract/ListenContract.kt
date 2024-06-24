package com.example.learnenglish.contract

import android.widget.TextView
import com.example.learnenglish.model.ListenAnswerModel
import com.example.learnenglish.model.ListenQuestionModel

interface ListenContract {
    interface View {
        fun showListenQuestion(mListQuestion: ArrayList<ListenQuestionModel>)
        fun showListenAnswer(mListAnswer: ArrayList<ListenAnswerModel>)
        fun showErrorMessage(message: String)
        fun showActivityFinished(totalNumberOfQuestion: Int, numCorrectAnswer: Int, point: Int)
        fun showResult(isCorrect: Boolean, textview: TextView)
        fun setData(pos: Int)
        fun showNumQuesCurent(pos: Int)
        fun showPoint(point: Int)
        fun showNextQuestion(listQuestion: ArrayList<ListenQuestionModel>, listAnswer: ArrayList<ListenAnswerModel>, newCurrentPos: Int)
    }
    interface Presenter {
        fun getItemsListenQuestion(): ArrayList<ListenQuestionModel>
        fun getItemsListenAnswer(): ArrayList<ListenAnswerModel>
        fun getUser(email: String, callback: TaskCallback.TaskCallbackUser2)
        fun updatePoint(id: String, point: Int)
        fun checkAnswer(textview: TextView, mListQues: ArrayList<ListenQuestionModel>,
                        mListAns: ArrayList<ListenAnswerModel>,currentPos: Int, point: Int)
    }
}