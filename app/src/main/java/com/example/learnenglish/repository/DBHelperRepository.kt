package com.example.learnenglish.repository

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.learnenglish.model.TopicModel
import com.example.learnenglish_demo.AnswerModel
import com.example.learnenglish_demo.QuestionModel
import java.io.File
import java.io.FileOutputStream

class DBHelperRepository(private val context: Context) {
    interface TaskCallback {
        fun onListQuestionLoaded(mListQuestion: ArrayList<QuestionModel>?)
        fun onListAnswerLoaded(mListAnswer: ArrayList<AnswerModel>?)
        fun onListTopicLoaded(mListTopic: ArrayList<TopicModel>?)
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
                val img = cursor.getString(cursor.getColumnIndex("AnhQues"))
                itemList.add(QuestionModel(questionID, content, answerID, topic, img))
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