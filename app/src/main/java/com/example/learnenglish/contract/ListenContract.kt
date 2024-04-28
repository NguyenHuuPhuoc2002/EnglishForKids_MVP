package com.example.learnenglish.contract

import android.widget.TextView
import com.example.learnenglish.model.ListenAnswerModel
import com.example.learnenglish.model.ListenQuestionModel
import com.example.learnenglish.model.VocabularyAnsModel
import com.example.learnenglish.model.VocabularyQuesModel
import com.example.learnenglish_demo.AnswerModel
import com.example.learnenglish_demo.QuestionModel

interface ListenContract {
    interface View {
        fun showListenQuestion(mListQuestion: ArrayList<ListenQuestionModel>)
        fun showListenAnswer(mListAnswer: ArrayList<ListenAnswerModel>)
        fun showErrorMessage(message: String)
        fun showActivityFinished(totalNumberOfQuestion: Int, numCorrectAnswer: Int, point: Int)
        fun showResult(isCorrect: Boolean, textview: TextView)
        fun setData(pos: Int)
        fun showNextQuestion(listQuestion: ArrayList<ListenQuestionModel>, listAnswer: ArrayList<ListenAnswerModel>, newCurrentPos: Int)
    }
    interface Presenter {
        fun getItemsListenQuestion(): ArrayList<ListenQuestionModel>
        fun getItemsListenAnswer(): ArrayList<ListenAnswerModel>
        fun checkAnswer(textview: TextView, mListQues: ArrayList<ListenQuestionModel>, mListAns: ArrayList<ListenAnswerModel>,currentPos: Int)
    }
}