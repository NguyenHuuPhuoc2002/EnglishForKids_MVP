package com.example.learnenglish.contract

import com.example.learnenglish.model.ListenAnswerModel
import com.example.learnenglish.model.ListenQuestionModel
import com.example.learnenglish.model.TopicModel
import com.example.learnenglish.model.VocabularyAnsModel
import com.example.learnenglish.model.VocabularyQuesModel
import com.example.learnenglish_demo.AnswerModel
import com.example.learnenglish_demo.QuestionModel

interface TaskCallback {
    interface TaskCallbackQuizzes {
        fun onListQuestionLoaded(mListQuestion: ArrayList<QuestionModel>?)
        fun onListAnswerLoaded(mListAnswer: ArrayList<AnswerModel>?)
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
}