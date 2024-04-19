package com.example.learnenglish.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import com.example.learnenglish.activity.VocabularyActivity
import com.example.learnenglish.contract.VocabularyContract
import com.example.learnenglish.model.TopicModel
import com.example.learnenglish.model.VocabularyAnsModel
import com.example.learnenglish.model.VocabularyQuesModel
import com.example.learnenglish.repository.DBHelperRepository
import com.example.learnenglish_demo.AnswerModel
import com.example.learnenglish_demo.QuestionModel


class VocabularyPresenter(private val context: Context, private val view: VocabularyActivity, private var db: DBHelperRepository) : VocabularyContract.Presenter {
    companion object {
        val handler = Handler(Looper.getMainLooper())
    }
    override fun getItemsVocabularyQuestion(): ArrayList<VocabularyQuesModel> {
        db = DBHelperRepository(context)
        db.openDatabase()
        val mList: ArrayList<VocabularyQuesModel> = ArrayList()
        db.getItemsQuestionVocabulary(object : DBHelperRepository.TaskCallback{
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
                if(mListVocQues != null){
                    for (i in 0 until mListVocQues.size) {
                        if(mListVocQues[i].topicID == view.id.toString()){
                            mList.add(mListVocQues[i])
                            view.showVocabularyQuestion(mList)
                        }
                    }
                }else {
                    view.showErrorMessage("Không tải được dữ liệu !")
                }
            }

            override fun onListVocabularyAnswerLoaded(mListVocAns: ArrayList<VocabularyAnsModel>?) {
                TODO("Not yet implemented")
            }

        })
        return mList
    }

    override fun getItemsVocabularyAnswer(): ArrayList<VocabularyAnsModel> {
        val mList: ArrayList<VocabularyAnsModel> = ArrayList()
        db.getItemsAnswerVocabulary(object : DBHelperRepository.TaskCallback{
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
            }

            override fun onListVocabularyAnswerLoaded(mListVocAns: ArrayList<VocabularyAnsModel>?) {
                if(mListVocAns != null){
                    for (i in 0 until mListVocAns.size) {
                        if(mListVocAns[i].topicID == view.id.toString()){
                            mList.add(mListVocAns[i])
                            view.showVocabularyAnswer(mList)
                        }
                    }
                }else {
                    view.showErrorMessage("Không tải được dữ liệu !")
                }
            }

        })
        return mList
    }

    override fun checkAnswer(textview: TextView, mListQues: ArrayList<VocabularyQuesModel>,
                                mListAns: ArrayList<VocabularyAnsModel>, currentPos: Int) {
        view.showResult(mListAns[currentPos].isCorrect == textview.text, textview)
        if(textview.text.isNotEmpty()){
            if(mListAns[currentPos].isCorrect == textview.text){
                nextQuestion(mListQues, mListAns, currentPos)
            }else{
                handler.postDelayed({
                    view.showActivityFinished(mListQues.size, currentPos, 0)
                },500)
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun nextQuestion(mListQues: ArrayList<VocabularyQuesModel>, mListAns: ArrayList<VocabularyAnsModel>,
                             newCurrentPos: Int) {
        if(newCurrentPos == mListQues.size - 1){
            handler.postDelayed({
                view.showActivityFinished(mListQues.size, newCurrentPos, 0)
            },500)
        }else{
            val incrementedPos = newCurrentPos + 1
            val tvNumQuesCurent = incrementedPos + 1
            handler.postDelayed({
                view.showNextQuestion(mListQues, mListAns, incrementedPos)
            },1000)
            handler.postDelayed({
                view.tvNumQuesCurent.text = "$tvNumQuesCurent "
            }, 1000)
        }
    }
}
