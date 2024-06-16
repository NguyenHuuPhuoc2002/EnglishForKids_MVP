package com.example.learnenglish.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.util.Log
import android.widget.TextView
import com.example.learnenglish.activity.QuizzesActivity
import com.example.learnenglish.contract.QuizzesContract
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.repository.DBHelperRepository
import com.example.learnenglish_demo.QuizzAnswerModel
import com.example.learnenglish.model.QuizzQuestionModel


class QuizzesPresenter(private val context: Context, private val view: QuizzesActivity, private var db: DBHelperRepository) : QuizzesContract.Presenter {
    override fun getItemsQuestion(): ArrayList<QuizzQuestionModel> {
        db = DBHelperRepository(context)
        db.openDatabase()
        val mListQuestions: ArrayList<QuizzQuestionModel> = ArrayList()
        db.getItemsQuestion(object : TaskCallback.TaskCallbackQuizzes {
            override fun onListQuestionLoaded(mListQuestion: ArrayList<QuizzQuestionModel>?) {
                if (mListQuestion != null) {
                    for (i in 0 until mListQuestion.size) {
                        if(mListQuestion[i].topic == view.id){
                            mListQuestions.add(mListQuestion[i])
                            view.showQuestion(mListQuestions)
                        }
                    }

                } else {
                    view.showErrorMessage("Không tải được dữ liệu !")
                }
            }

            override fun onListAnswerLoaded(mListAnswer: ArrayList<QuizzAnswerModel>?) {
                TODO("Not yet implemented")
            }

        })
        return mListQuestions
    }


    override fun getItemsAnswer(): ArrayList<QuizzAnswerModel> {
        val mListAnswers: ArrayList<QuizzAnswerModel> = ArrayList()
        db.getItemsAnswer(object : TaskCallback.TaskCallbackQuizzes{
            override fun onListQuestionLoaded(mListQuestion: ArrayList<QuizzQuestionModel>?) {
                TODO("Not yet implemented")
            }
            override fun onListAnswerLoaded(mListAnswer: ArrayList<QuizzAnswerModel>?) {
                if (mListAnswer != null) {
                    for (i in 0 until mListAnswer.size) {
                        if(mListAnswer[i].topic == view.id){
                            mListAnswers.add(mListAnswer[i])
                            Log.d("chu de", mListAnswer[i].topic)
                            view.showAnswer(mListAnswers)
                        }
                    }
                } else {
                    view.showErrorMessage("Không tải được dữ liệu !")
                }
            }

        })
        return mListAnswers
    }

    override fun checkAnswer(textview: TextView, mListAns: ArrayList<QuizzAnswerModel>, mListQues: ArrayList<QuizzQuestionModel>, currentPos: Int){
        view.showResult(mListAns[currentPos].isCorrect == textview.text, textview)
        if(mListAns[currentPos].isCorrect == textview.text){
            nextQuestion(mListQues, mListAns, currentPos)
        }else{
            Handler().postDelayed({
                view.showActivityFinished(mListQues.size, currentPos, 0)
            },1000)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun nextQuestion(mListQues: ArrayList<QuizzQuestionModel>, listAnswer: ArrayList<QuizzAnswerModel>, newCurrentPos: Int) {
        if(newCurrentPos == mListQues.size - 1){
            Handler().postDelayed({
                val newPos = newCurrentPos + 1
                view.showActivityFinished(mListQues.size, newPos, 0)
            },1000)
        }else{
            val incrementedPos = newCurrentPos + 1
            val newNumQuesCurent = incrementedPos + 1
            view.showNextQuestion(mListQues, listAnswer, incrementedPos)
            Handler().postDelayed({
                view.showNumQuesCurent(newNumQuesCurent)
            }, 1000)
        }
    }

}
