package com.example.learnenglish.presenter

import android.content.Context
import android.util.Log
import com.example.learnenglish.activity.MainActivity
import com.example.learnenglish.activity.TopicActivity
import com.example.learnenglish.contract.TopicContract
import com.example.learnenglish.model.TopicModel
import com.example.learnenglish.repository.DBHelperRepository
import com.example.learnenglish_demo.AnswerModel
import com.example.learnenglish_demo.QuestionModel

class TopicPresenter(private val context: Context, private val view: TopicActivity, private var db: DBHelperRepository): TopicContract.Presenter {
    override fun getItemsTopic(): ArrayList<TopicModel> {
        db = DBHelperRepository(context)
        db.openDatabase()
        val mListTopics: ArrayList<TopicModel> = ArrayList()
        db.getItemsTopic(object : DBHelperRepository.TaskCallback {
            override fun onListQuestionLoaded(mListQuestion: ArrayList<QuestionModel>?) {

            }

            override fun onListAnswerLoaded(mListAnswer: ArrayList<AnswerModel>?) {

            }

            override fun onListTopicLoaded(mListTopic: ArrayList<TopicModel>?) {
                if (mListTopic != null) {
                    for (i in 0 until mListTopic.size) {
                        // if(mListAnswer[i].topic == view.title_){
                        mListTopics.add(mListTopic[i])
                        Log.d("chu de", mListTopic[i].img.toString())
                        view.showTopic(mListTopics)
                        // }
                    }
                } else {
                    view.showErrorMessage("Không tải được dữ liệu !")
                }
            }
        })
        return mListTopics
    }
}