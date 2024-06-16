package com.example.learnenglish.contract

import android.widget.TextView
import com.example.learnenglish.model.SentencesSortAnswerModel
import com.example.learnenglish.model.SentencesSortQuesModel

interface SentencesSortContract {
    interface View{
        fun showQuestion(mListQuestion: ArrayList<SentencesSortQuesModel>)
        fun showAnswer(mListAnswer: ArrayList<SentencesSortAnswerModel>)
        fun showErrorMessage(message: String)
        fun showActivityFinished(totalNumberOfQuestion: Int, numCorrectAnswer: Int, point: Int)
        fun showResult(isCorrect: Boolean, textview: TextView)
        fun showNumQuesCurent(pos: Int)
        fun showNextQuestion(listQuestion: ArrayList<SentencesSortQuesModel>, mAnswer: SentencesSortAnswerModel, newCurrentPos: Int)
    }
    interface Presenter {
        fun getItemsQuestion(): ArrayList<SentencesSortQuesModel>
        fun getItemsAnswer(): ArrayList<SentencesSortAnswerModel>
        fun checkAnswer(textview: TextView, mListQues: ArrayList<SentencesSortQuesModel>, mListAns: SentencesSortAnswerModel, currentPos: Int)
    }
}