package com.example.learnenglish.contract

import android.widget.TextView
import com.example.learnenglish.model.VocabularyAnsModel
import com.example.learnenglish.model.VocabularyQuesModel
import com.example.learnenglish_demo.AnswerModel
import com.example.learnenglish_demo.QuestionModel

interface VocabularyContract {
    interface View {
        fun showVocabularyQuestion(mListQuestion: ArrayList<VocabularyQuesModel>)
        fun showVocabularyAnswer(mListAnswer: ArrayList<VocabularyAnsModel>)
        fun showErrorMessage(message: String)
        fun showActivityFinished(totalNumberOfQuestion: Int, numCorrectAnswer: Int, point: Int)
        fun showResult(isCorrect: Boolean, textview: TextView)
        fun showNextQuestion(listQuestion: ArrayList<VocabularyQuesModel>, listAnswer: ArrayList<VocabularyAnsModel>, newCurrentPos: Int)
    }
    interface Presenter {
        fun getItemsVocabularyQuestion(): ArrayList<VocabularyQuesModel>
        fun getItemsVocabularyAnswer(): ArrayList<VocabularyAnsModel>
        fun checkAnswer(textview: TextView, mListQues: ArrayList<VocabularyQuesModel>, mListAns: ArrayList<VocabularyAnsModel>,currentPos: Int)
    }
}