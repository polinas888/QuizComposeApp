package com.example.quizcomposeapp.network

import com.example.quizcomposeapp.model.Questions
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionApi {
    @GET("world.json")
    suspend fun getQuestions(): Questions
}