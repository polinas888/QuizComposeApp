package com.example.quizcomposeapp.repository

import android.util.Log
import com.example.quizcomposeapp.data.DataResult
import com.example.quizcomposeapp.model.QuestionItem
import com.example.quizcomposeapp.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api:QuestionApi) {
    private val questionListResult = DataResult<ArrayList<QuestionItem>, Boolean, Exception>()

    suspend fun getListOfQuestions(): DataResult<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            questionListResult.data = api.getQuestions()
        } catch (e: Exception) {
            questionListResult.exception = e
            Log.e("Exception", "get all questions ${questionListResult.exception!!.localizedMessage}")
        }
        return questionListResult
    }
}