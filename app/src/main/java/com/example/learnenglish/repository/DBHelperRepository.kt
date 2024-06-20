package com.example.learnenglish.repository

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.Toast
import com.example.learnenglish.contract.TaskCallback
import com.example.learnenglish.fragment.RegisterFragment
import com.example.learnenglish.model.ListenAnswerModel
import com.example.learnenglish.model.ListenQuestionModel
import com.example.learnenglish.model.TopicModel
import com.example.learnenglish.model.VocabularyAnsModel
import com.example.learnenglish.model.VocabularyQuesModel
import com.example.learnenglish_demo.QuizzAnswerModel
import com.example.learnenglish.model.QuizzQuestionModel
import com.example.learnenglish.model.SentencesSortAnswerModel
import com.example.learnenglish.model.SentencesSortQuesModel
import com.example.learnenglish.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.File
import java.io.FileOutputStream

class DBHelperRepository(private val context: Context) {

    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    companion object{
        private const val DB_NAME = "ENGLISHDB.db"
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
    fun getItemsQuestion(callback: TaskCallback.TaskCallbackQuizzes) {
        val itemList: ArrayList<QuizzQuestionModel> = arrayListOf()
        val db = openDatabase()
        val cursor = db.rawQuery("SELECT * FROM QUESTIONS", null)
        if (cursor.moveToFirst()) {
            do {
                val questionID = cursor.getInt(cursor.getColumnIndex("QuestionID"))
                val content = cursor.getString(cursor.getColumnIndex("Content"))
                val answerID = cursor.getInt(cursor.getColumnIndex("AnswerID"))
                val topic = cursor.getString(cursor.getColumnIndex("TopicID"))
              //  val img = cursor.getString(cursor.getColumnIndex("AnhQues"))
                itemList.add(QuizzQuestionModel(questionID, content, answerID, topic))
            } while (cursor.moveToNext())
        }
        cursor.close()
        callback.onListQuestionLoaded(itemList)
    }

    @SuppressLint("Range")
    fun getItemsAnswer(callback: TaskCallback.TaskCallbackQuizzes) {
        val itemList: ArrayList<QuizzAnswerModel> = arrayListOf()
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
                itemList.add(QuizzAnswerModel(answerID, A, B, C, D, isCorrect, topic))
            } while (cursor.moveToNext())
        }
        cursor.close()
        callback.onListAnswerLoaded(itemList)
    }
    @SuppressLint("Range")
    fun getItemsQuestionVocabulary(callback: TaskCallback.TaskCallbackVocabulary) {
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
    fun getItemsAnswerVocabulary(callback: TaskCallback.TaskCallbackVocabulary) {
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
    fun getItemsQuestionsListen(callback: TaskCallback.TaskCallbackListen) {
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
    fun getItemsAnswersListen(callback: TaskCallback.TaskCallbackListen) {
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
    fun getItemsQuesSentencesSort(callback: TaskCallback.TaskCallbackSentencesSort) {
        val itemList: ArrayList<SentencesSortQuesModel> = arrayListOf()
        val db = openDatabase()
        val cursor = db.rawQuery("SELECT * FROM SENTENCESSORTQUESTIONS", null)
        if (cursor.moveToFirst()) {
            do {
                val quesID = cursor.getInt(cursor.getColumnIndex("QuestionID"))
                val content = cursor.getString(cursor.getColumnIndex("Content"))
                val topicID = cursor.getString(cursor.getColumnIndex("TopicID"))
                val answerID = cursor.getInt(cursor.getColumnIndex("AnswerID"))
                itemList.add(SentencesSortQuesModel(quesID, content, answerID, topicID))
            } while (cursor.moveToNext())
        }
        cursor.close()
        callback.onListSentencesSortQuestionLoaded(itemList)
    }
    @SuppressLint("Range")
    fun getItemsAnsersSentencesSort(callback: TaskCallback.TaskCallbackSentencesSort){
        val itemList: ArrayList<SentencesSortAnswerModel> = arrayListOf()
        val db = openDatabase()
        val cursor = db.rawQuery("SELECT * FROM SENTENCESSORTANSWERS", null)
        if(cursor.moveToFirst()){
            do{
                val ansID = cursor.getInt(cursor.getColumnIndex("AnswerID"))
                val correct = cursor.getString(cursor.getColumnIndex("isCorrect"))
                val answerA = cursor.getString(cursor.getColumnIndex("A"))
                val answerB = cursor.getString(cursor.getColumnIndex("B"))
                val answerC = cursor.getString(cursor.getColumnIndex("C"))
                val answerD = cursor.getString(cursor.getColumnIndex("D"))
                val topicID = cursor.getString(cursor.getColumnIndex("TopicID"))
                itemList.add(SentencesSortAnswerModel(ansID, answerA, answerB, answerC, answerD, correct, topicID))
            } while (cursor.moveToNext())
        }
        cursor.close()
        callback.onListSentencesSortAnswerLoaded(itemList)
    }
    @SuppressLint("Range")
    fun getItemsTopic(callback: TaskCallback.TaskCallbackTopic) {
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

    @SuppressLint("SuspiciousIndentation")
    fun registerDb(email: String, name: String, password: String, callback: TaskCallback.TaskCallbackRegister){
        firebaseAuth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("Users")
        val id = dbRef.push().key
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = UserModel(id, name, email, 0, 0)
                        dbRef.child(id!!).setValue(user)
                            .addOnSuccessListener {

                                callback.showRegisterSuccess("Đăng kí thành công")
                            }
                            .addOnFailureListener {

                                callback.showRegisterFail("Đăng kí không thành công!")
                            }
                    } else {
                        val errorMessage = task.exception?.message
                        callback.showRegisterFail("Lỗi: $errorMessage")
                    }
                }
    }

    fun getLogIn(email: String, passWord: String, callback: TaskCallback.TaskCallbackLogin){
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email, passWord).addOnCompleteListener {task ->
            if (task.isSuccessful) {
                callback.onLogInSuccess("Đăng nhập thành công!")
            } else {
                callback.onLogInFail("Đăng nhập thất bại!")
            }
        }
    }


}