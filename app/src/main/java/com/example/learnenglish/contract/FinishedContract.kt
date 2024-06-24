package com.example.learnenglish.contract

interface FinishedContract {
    interface Presenter{
        fun updatePoint(id: String, point: Int)
    }
}