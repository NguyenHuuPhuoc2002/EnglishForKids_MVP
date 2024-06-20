package com.example.learnenglish.contract

import android.app.AlertDialog
import com.example.learnenglish.model.ListenAnswerModel
import com.example.learnenglish.model.ListenQuestionModel
import com.example.learnenglish.model.TopicModel
import com.example.learnenglish.model.VocabularyAnsModel
import com.example.learnenglish.model.VocabularyQuesModel
import com.example.learnenglish_demo.QuizzAnswerModel
import com.example.learnenglish.model.QuizzQuestionModel
import com.example.learnenglish.model.SentencesSortAnswerModel
import com.example.learnenglish.model.SentencesSortQuesModel
import java.lang.StringBuilder

interface TaskCallback {
    interface TaskCallbackQuizzes {
        fun onListQuestionLoaded(mListQuestion: ArrayList<QuizzQuestionModel>?)
        fun onListAnswerLoaded(mListAnswer: ArrayList<QuizzAnswerModel>?)
    }
    interface TaskCallbackTopic {
        fun onListTopicLoaded(mListTopic: ArrayList<TopicModel>?)
    }
    interface TaskCallbackVocabulary {
        fun onListVocabularyQuestionLoaded(mListVocQues: ArrayList<VocabularyQuesModel>?)
        fun onListVocabularyAnswerLoaded(mListVocAns: ArrayList<VocabularyAnsModel>?)
    }
    interface TaskCallbackListen{
        fun onListListenQuestionLoaded(mListLisQues: ArrayList<ListenQuestionModel>?)
        fun onListListenAnswerLoaded(mListLisAns: ArrayList<ListenAnswerModel>?)
    }
    interface TaskCallbackSentencesSort{
        fun onListSentencesSortQuestionLoaded(mListQues: ArrayList<SentencesSortQuesModel>?)
        fun onListSentencesSortAnswerLoaded(mListAns: ArrayList<SentencesSortAnswerModel>?)
    }
    interface TaskCallbackRegister{
        fun showRegisterSuccess(message: String)
        fun showRegisterFail(message: String)
    }

    interface TaskCallbackLogin{
        fun onLogInSuccess(message: String)
        fun onLogInFail(message: String)
    }
}