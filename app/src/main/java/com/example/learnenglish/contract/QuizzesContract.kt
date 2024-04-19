package com.example.learnenglish.contract

import android.widget.TextView
import com.example.learnenglish_demo.AnswerModel
import com.example.learnenglish_demo.QuestionModel


interface QuizzesContract {
    interface OnClickListener {
        fun onClickListenerItemHome(pos: Int)
    }
    interface View {
        fun showQuestion(mListQuestion: ArrayList<QuestionModel>)
        fun showAnswer(mListAnswer: ArrayList<AnswerModel>)
        fun showQuizEndMessage()
        fun showErrorMessage(message: String)
        fun showActivityFinished(totalNumberOfQuestion: Int, numCorrectAnswer: Int, point: Int)
        fun showResult(isCorrect: Boolean, textview: TextView)
        fun showNextQuestion(listQuestion: ArrayList<QuestionModel>, listAnswer: ArrayList<AnswerModel>, newCurrentPos: Int)
    }

    interface Presenter {
        fun getItemsQuestion(): ArrayList<QuestionModel>
        fun getItemsAnswer(): ArrayList<AnswerModel>
        fun checkAnswer(textview: TextView, mListAns: ArrayList<AnswerModel>, mListQues: ArrayList<QuestionModel>, currentPos: Int)
    }
}