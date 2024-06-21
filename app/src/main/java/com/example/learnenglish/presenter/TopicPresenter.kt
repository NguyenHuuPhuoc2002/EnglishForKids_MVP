package com.example.learnenglish.presenter

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.learnenglish.activity.ListenActivity
import com.example.learnenglish.activity.QuizzesActivity
import com.example.learnenglish.activity.SentencesSortActivity
import com.example.learnenglish.activity.TopicActivity
import com.example.learnenglish.activity.VocabularyActivity
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.contract.TopicContract
import com.example.learnenglish.model.TopicModel
import com.example.learnenglish.repository.DBHelperRepository

class TopicPresenter(private val context: Context, private val view: TopicActivity, private var db: DBHelperRepository): TopicContract.Presenter {
    private var activity = view as Activity
    override fun getItemsTopic(): ArrayList<TopicModel> {
        db = DBHelperRepository(context)
        db.openDatabase()
        val mListTopics: ArrayList<TopicModel> = ArrayList()
        db.getItemsTopic(object : TaskCallback.TaskCallbackTopic {
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

    override fun onStartActivity(str: String, email: String, id: String) {
        if(str == "Trắc Nghiệm"){
            view.showActivity(email, id, str)
        }else if(str == "Từ Vựng"){
            view.showActivity(email, id, str)
        }else if(str == "Luyện Nghe"){
            view.showActivity(email, id, str)
        }else if(str == "Sắp Xếp Câu") {
            view.showActivity(email, id, str)
        }
    }
}