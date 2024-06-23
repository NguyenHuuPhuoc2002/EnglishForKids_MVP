package com.example.learnenglish.contract

import android.widget.TextView
import com.example.learnenglish_demo.QuizzAnswerModel
import com.example.learnenglish.model.QuizzQuestionModel
import com.example.learnenglish.model.UserModel


interface QuizzesContract {
    interface OnClickListener {
        fun onClickListenerItemHome(pos: Int)
    }
    interface View {
        fun showQuestion(mListQuestion: ArrayList<QuizzQuestionModel>)
        fun showAnswer(mListAnswer: ArrayList<QuizzAnswerModel>)
        fun showQuizEndMessage()
        fun showErrorMessage(message: String)
        fun showActivityFinished(totalNumberOfQuestion: Int, numCorrectAnswer: Int, point: Int)
        fun showResult(isCorrect: Boolean, textview: TextView)
        fun showNumQuesCurent(pos: Int)
        fun showPoint(point: Int)
        fun showNextQuestion(listQuestion: ArrayList<QuizzQuestionModel>,
                             listAnswer: ArrayList<QuizzAnswerModel>, newCurrentPos: Int)
    }

    interface Presenter {
        fun getItemsQuestion(): ArrayList<QuizzQuestionModel>
        fun getItemsAnswer(): ArrayList<QuizzAnswerModel>
        fun getUser(email: String, callback: TaskCallback.TaskCallbackUser2)
        fun updatePoint(id: String, point: Int)
        fun checkAnswer(textview: TextView, mListAns: ArrayList<QuizzAnswerModel>,
                        mListQues: ArrayList<QuizzQuestionModel>, currentPos: Int, point: Int) : Int
    }
}