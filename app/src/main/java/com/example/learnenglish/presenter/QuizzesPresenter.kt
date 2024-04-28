package com.example.learnenglish.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.util.Log
import android.widget.TextView
import com.example.learnenglish.activity.QuizzesActivity
import com.example.learnenglish.contract.QuizzesContract
import com.example.learnenglish.model.ListenAnswerModel
import com.example.learnenglish.model.ListenQuestionModel
import com.example.learnenglish.model.TopicModel
import com.example.learnenglish.model.VocabularyAnsModel
import com.example.learnenglish.model.VocabularyQuesModel
import com.example.learnenglish.repository.DBHelperRepository
import com.example.learnenglish_demo.AnswerModel
import com.example.learnenglish_demo.QuestionModel


class QuizzesPresenter(private val context: Context, private val view: QuizzesActivity, private var db: DBHelperRepository) : QuizzesContract.Presenter {
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
                TODO("Not yet implemented")
            }

        })
        return mListAnswers
    }

    override fun checkAnswer(textview: TextView, mListAns: ArrayList<AnswerModel>, mListQues: ArrayList<QuestionModel>, currentPos: Int){
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
    private fun nextQuestion(mListQues: ArrayList<QuestionModel>, listAnswer: ArrayList<AnswerModel>, newCurrentPos: Int) {
        if(newCurrentPos == mListQues.size - 1){
            Handler().postDelayed({
                val newPos = newCurrentPos + 1
                view.showActivityFinished(mListQues.size, newPos, 0)
            },1000)
        }else{
            val incrementedPos = newCurrentPos + 1
            val tvNumQuesCurent = incrementedPos + 1
            view.showNextQuestion(mListQues, listAnswer, incrementedPos)
            Handler().postDelayed({
                view.tvNumQuesCurent.text = "$tvNumQuesCurent "
            }, 1000)
        }
    }

}
