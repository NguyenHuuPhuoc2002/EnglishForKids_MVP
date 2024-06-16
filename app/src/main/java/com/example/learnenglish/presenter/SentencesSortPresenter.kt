package com.example.learnenglish.presenter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import com.example.learnenglish.activity.SentencesSortActivity
import com.example.learnenglish.contract.SentencesSortContract
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.model.QuizzQuestionModel
import com.example.learnenglish.model.SentencesSortAnswerModel
import com.example.learnenglish.model.SentencesSortQuesModel
import com.example.learnenglish.model.VocabularyAnsModel
import com.example.learnenglish.model.VocabularyQuesModel
import com.example.learnenglish.repository.DBHelperRepository
import com.example.learnenglish_demo.QuizzAnswerModel
import java.lang.ref.WeakReference

class SentencesSortPresenter(private val context: Context, private val view: SentencesSortActivity, private var db: DBHelperRepository) : SentencesSortContract.Presenter{
    companion object {
        val handler = Handler(Looper.getMainLooper())
    }
    override fun getItemsQuestion(): ArrayList<SentencesSortQuesModel> {
        db = DBHelperRepository(context)
        db.openDatabase()
        val mListQuestions: ArrayList<SentencesSortQuesModel> = ArrayList()
        db.getItemsQuesSentencesSort(object : TaskCallback.TaskCallbackSentencesSort{
            override fun onListSentencesSortQuestionLoaded(mListQues: ArrayList<SentencesSortQuesModel>?) {
               if(mListQues != null){
                   for(i in 0 until  mListQues.size){
                       if(mListQues[i].topic == view.id){
                           mListQuestions.add(mListQues[i])
                           view.showQuestion(mListQuestions)
                       }
                   }
               }else{
                   view.showErrorMessage("Không tải được dữ liệu !")
               }
            }

            override fun onListSentencesSortAnswerLoaded(mListAns: ArrayList<SentencesSortAnswerModel>?) {
                TODO("Not yet implemented")
            }

        })
        return mListQuestions
    }

    override fun getItemsAnswer(): ArrayList<SentencesSortAnswerModel> {
        val mListAnswers: ArrayList<SentencesSortAnswerModel> = ArrayList()
        db.getItemsAnsersSentencesSort(object : TaskCallback.TaskCallbackSentencesSort{
            override fun onListSentencesSortQuestionLoaded(mListQues: ArrayList<SentencesSortQuesModel>?) {
                TODO("Not yet implemented")
            }

            override fun onListSentencesSortAnswerLoaded(mListAns: ArrayList<SentencesSortAnswerModel>?) {
                if (mListAns != null) {
                    for (i in 0 until mListAns.size) {
                        if(mListAns[i].topic == view.id){
                            mListAnswers.add(mListAns[i])
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

    override fun checkAnswer(
        textview: TextView,
        mListQues: ArrayList<SentencesSortQuesModel>,
        mListAns: SentencesSortAnswerModel,
        currentPos: Int
    ) {
        view.showResult(mListAns.isCorrect.trim() == textview.text.trim(), textview)
        if(textview.text.isNotEmpty()){
            if(mListAns.isCorrect.trim() == textview.text.trim()){
                nextQuestion(mListQues, mListAns, currentPos)
            }else{
                handler.postDelayed({
                    view.showActivityFinished(mListQues.size, currentPos, 0)
                },1000)
            }
        }
    }
    private fun nextQuestion(mListQues: ArrayList<SentencesSortQuesModel>, mListAns: SentencesSortAnswerModel,
                             newCurrentPos: Int){
        if(newCurrentPos == mListQues.size - 1){
            val newPos = newCurrentPos + 1
            handler.postDelayed({
                view.showActivityFinished(mListQues.size, newPos, 0)
            }, 1000)
        }else{
            val incrementedPos = newCurrentPos + 1
            val newNumQuesCurent = incrementedPos + 1
            handler.postDelayed({
                view.showNextQuestion(mListQues, mListAns, incrementedPos)
            },1000)
            handler.postDelayed({
                view.showNumQuesCurent(newNumQuesCurent)
            }, 1000)
        }
    }
}