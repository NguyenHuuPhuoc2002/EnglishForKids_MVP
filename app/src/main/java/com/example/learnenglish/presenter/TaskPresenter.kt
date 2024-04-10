package com.example.learnenglish.presenter

import android.content.Context
import android.os.Handler
import android.util.Log
import android.widget.TextView
import com.example.learnenglish.activity.MainActivity
import com.example.learnenglish.contract.TaskContract
import com.example.learnenglish.model.TopicModel
import com.example.learnenglish.repository.DBHelperRepository
import com.example.learnenglish_demo.AnswerModel
import com.example.learnenglish_demo.QuestionModel


class TaskPresenter(private val context: Context, private val view: MainActivity, private var db: DBHelperRepository) : TaskContract.Presenter {
    override fun getItemsQuestion(): ArrayList<QuestionModel> {
        db = DBHelperRepository(context)
        db.openDatabase()
        val mListQuestions: ArrayList<QuestionModel> = ArrayList()
        db.getItemsQuestion(object : DBHelperRepository.TaskCallback {
            override fun onListQuestionLoaded(mListQuestion: ArrayList<QuestionModel>?) {
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

            override fun onListAnswerLoaded(mListAnswer: ArrayList<AnswerModel>?) {
                TODO("Not yet implemented")
            }

            override fun onListTopicLoaded(mListTopic: ArrayList<TopicModel>?) {
                TODO("Not yet implemented")
            }


        })
        return mListQuestions
    }


    override fun getItemsAnswer(): ArrayList<AnswerModel> {
        val mListAnswers: ArrayList<AnswerModel> = ArrayList()
        db.getItemsAnswer(object : DBHelperRepository.TaskCallback{
            override fun onListQuestionLoaded(mListQuestion: ArrayList<QuestionModel>?) {
                TODO("Not yet implemented")
            }
            override fun onListAnswerLoaded(mListAnswer: ArrayList<AnswerModel>?) {
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

            override fun onListTopicLoaded(mListTopic: ArrayList<TopicModel>?) {
                TODO("Not yet implemented")
            }

        })
        return mListAnswers
    }

    override fun checkAnswer(textview: TextView, mListAns: ArrayList<AnswerModel>, mListQues: ArrayList<QuestionModel>, currentPos: Int){
        view.showResult(mListAns[currentPos].isCorrect == textview.text, textview)
        Log.d("ketqua 1", mListAns[currentPos].isCorrect + textview.text)
        if(mListAns[currentPos].isCorrect == textview.text){
            nextQuestion(mListQues, mListAns, currentPos)
        }
    }

    private fun nextQuestion(mListQues: ArrayList<QuestionModel>, listAnswer: ArrayList<AnswerModel>, newCurrentPos: Int) {
        Handler().postDelayed({
            if(newCurrentPos == mListQues.size - 1){
                view.showQuizEndMessage()
            }else{
                val incrementedPos = newCurrentPos + 1
                view.showNextQuestion(mListQues, listAnswer, incrementedPos)
            }
        }, 1400)
    }

}
