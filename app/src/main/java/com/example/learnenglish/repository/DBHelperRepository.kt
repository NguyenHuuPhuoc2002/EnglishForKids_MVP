package com.example.learnenglish.repository

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.learnenglish.model.ListenAnswerModel
import com.example.learnenglish.model.ListenQuestionModel
import com.example.learnenglish.model.TopicModel
import com.example.learnenglish.model.VocabularyAnsModel
import com.example.learnenglish.model.VocabularyQuesModel
import com.example.learnenglish_demo.AnswerModel
import com.example.learnenglish_demo.QuestionModel
import java.io.File
import java.io.FileOutputStream

class DBHelperRepository(private val context: Context) {
    interface TaskCallback {
        fun onListQuestionLoaded(mListQuestion: ArrayList<QuestionModel>?)
        fun onListAnswerLoaded(mListAnswer: ArrayList<AnswerModel>?)
        fun onListTopicLoaded(mListTopic: ArrayList<TopicModel>?)
        fun onListVocabularyQuestionLoaded(mListVocQues: ArrayList<VocabularyQuesModel>?)
        fun onListVocabularyAnswerLoaded(mListVocAns: ArrayList<VocabularyAnsModel>?)
        fun onListListenQuestionLoaded(mListLisQues: ArrayList<ListenQuestionModel>?)
        fun onListListenAnswerLoaded(mListLisAns: ArrayList<ListenAnswerModel>?)
    }
    companion object{
        private val DB_NAME = "ENGLISHDB.db"
    }
    fun openDatabase(): SQLiteDatabase{
        val dbFile = context.getDatabasePath(DB_NAME)
        val file = File(dbFile.toString())
        if(file.exists()){
            //không làm gì cả
            Log.e("tuhoc", "file đã tồn tại!")
        }else{
            copyDatabase(dbFile)
        }
        return SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    private fun copyDatabase(dbFile: File?) {
        val openDB = context.assets.open(DB_NAME)
        val outputStream = FileOutputStream(dbFile)
        val buffer = ByteArray(1024)
        while(openDB.read(buffer) > 0){
            outputStream.write(buffer)
            Log.wtf("DB", "Writing")
        }
        outputStream.flush()
        outputStream.close()
        openDB.close()
        Log.wtf("DB", "Copy DB Complete")
    }
    @SuppressLint("Range")
    fun getItemsQuestion(callback: TaskCallback) {
        val itemList: ArrayList<QuestionModel> = arrayListOf()
        val db = openDatabase()
        val cursor = db.rawQuery("SELECT * FROM QUESTIONS", null)
        if (cursor.moveToFirst()) {
            do {
                val questionID = cursor.getInt(cursor.getColumnIndex("QuestionID"))
                val content = cursor.getString(cursor.getColumnIndex("Content"))
                val answerID = cursor.getInt(cursor.getColumnIndex("AnswerID"))
                val topic = cursor.getString(cursor.getColumnIndex("TopicID"))
              //  val img = cursor.getString(cursor.getColumnIndex("AnhQues"))
                itemList.add(QuestionModel(questionID, content, answerID, topic))
            } while (cursor.moveToNext())
        }
        cursor.close()
        callback.onListQuestionLoaded(itemList)
    }

    @SuppressLint("Range")
    fun getItemsAnswer(callback: TaskCallback) {
        val itemList: ArrayList<AnswerModel> = arrayListOf()
        val db = openDatabase()
        val cursor = db.rawQuery("SELECT * FROM ANSWERS", null)
        if (cursor.moveToFirst()) {
            do {
                val answerID = cursor.getInt(cursor.getColumnIndex("AnswerID"))
                val A = cursor.getString(cursor.getColumnIndex("A"))
                val B = cursor.getString(cursor.getColumnIndex("B"))
                val C = cursor.getString(cursor.getColumnIndex("C"))
                val D = cursor.getString(cursor.getColumnIndex("D"))
                val isCorrect = cursor.getString(cursor.getColumnIndex("isCorrect"))
                val topic = cursor.getString(cursor.getColumnIndex("TopicID"))
                itemList.add(AnswerModel(answerID, A, B, C, D, isCorrect, topic))
            } while (cursor.moveToNext())
        }
        cursor.close()
        callback.onListAnswerLoaded(itemList)
    }
    @SuppressLint("Range")
    fun getItemsQuestionVocabulary(callback: TaskCallback) {
        val itemList: ArrayList<VocabularyQuesModel> = arrayListOf()
        val db = openDatabase()
        val cursor = db.rawQuery("SELECT * FROM VOCABULARYQUESTIONS", null)
        if (cursor.moveToFirst()) {
            do {
                val quesID = cursor.getInt(cursor.getColumnIndex("QuestionID"))
                val voc = cursor.getString(cursor.getColumnIndex("VocabularyCharacter"))
                val topicID = cursor.getString(cursor.getColumnIndex("TopicID"))
                val img = cursor.getString(cursor.getColumnIndex("Anh"))
                val mean = cursor.getString(cursor.getColumnIndex("Meaning"))
                itemList.add(VocabularyQuesModel(quesID, voc, topicID, img, mean))
            } while (cursor.moveToNext())
        }
        cursor.close()
        callback.onListVocabularyQuestionLoaded(itemList)
    }
    @SuppressLint("Range")
    fun getItemsAnswerVocabulary(callback: TaskCallback) {
        val itemList: ArrayList<VocabularyAnsModel> = arrayListOf()
        val db = openDatabase()
        val cursor = db.rawQuery("SELECT * FROM VOCABULARYANSWERS", null)
        if (cursor.moveToFirst()) {
            do {
                val quesID = cursor.getInt(cursor.getColumnIndex("QuestionID"))
                val isCorrect = cursor.getString(cursor.getColumnIndex("isCorrectVocabulary"))
                val topic = cursor.getString(cursor.getColumnIndex("TopicID"))
                val audio = cursor.getBlob(cursor.getColumnIndex("Audio"))
                itemList.add(VocabularyAnsModel(quesID, isCorrect, topic, audio))
            } while (cursor.moveToNext())
        }
        cursor.close()
        callback.onListVocabularyAnswerLoaded(itemList)
    }
    @SuppressLint("Range")
    fun getItemsQuestionsListen(callback: TaskCallback) {
        val itemList: ArrayList<ListenQuestionModel> = arrayListOf()
        val db = openDatabase()
        val cursor = db.rawQuery("SELECT * FROM LISTENQUESTIONS", null)
        if (cursor.moveToFirst()) {
            do {
                val quesID = cursor.getInt(cursor.getColumnIndex("QuestionID"))
                val content = cursor.getString(cursor.getColumnIndex("Content"))
                val topicID = cursor.getString(cursor.getColumnIndex("TopicID"))
                val audio = cursor.getBlob(cursor.getColumnIndex("Audio"))
                itemList.add(ListenQuestionModel(quesID, content, topicID, audio))
            } while (cursor.moveToNext())
        }
        cursor.close()
        callback.onListListenQuestionLoaded(itemList)
    }
    @SuppressLint("Range")
    fun getItemsAnswersListen(callback: TaskCallback) {
        val itemList: ArrayList<ListenAnswerModel> = arrayListOf()
        val db = openDatabase()
        val cursor = db.rawQuery("SELECT * FROM LISTENANSWERS", null)
        if (cursor.moveToFirst()) {
            do {
                val quesID = cursor.getInt(cursor.getColumnIndex("QuestionID"))
                val correct = cursor.getString(cursor.getColumnIndex("isCorrect"))
                val topicID = cursor.getString(cursor.getColumnIndex("TopicID"))
                val audio = cursor.getBlob(cursor.getColumnIndex("Audio"))
                itemList.add(ListenAnswerModel(quesID, correct, topicID, audio))
            } while (cursor.moveToNext())
        }
        cursor.close()
        callback.onListListenAnswerLoaded(itemList)
    }
    @SuppressLint("Range")
    fun getItemsTopic(callback: TaskCallback) {
        val itemList: ArrayList<TopicModel> = arrayListOf()
        val db = openDatabase()
        val cursor = db.rawQuery("SELECT * FROM TOPIC", null)
        if (cursor.moveToFirst()) {
            do {
                val topicID = cursor.getString(cursor.getColumnIndex("TopicID"))
                val img = cursor.getString(cursor.getColumnIndex("Anh"))
                val topicName = cursor.getString(cursor.getColumnIndex("TopicName"))

                itemList.add(TopicModel(topicID, img, topicName))
            } while (cursor.moveToNext())
        }
        cursor.close()

        callback.onListTopicLoaded(itemList)
    }




}