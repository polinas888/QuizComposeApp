package com.example.quizcomposeapp.di

import com.example.quizcomposeapp.network.QuestionApi
import com.example.quizcomposeapp.repository.QuestionRepository
import com.example.quizcomposeapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // add module to component
object AppModule {
    @Singleton
    @Provides
    fun provideQuestionRepository(api: QuestionApi) : QuestionRepository {
        return QuestionRepository(api)
    }

    @Singleton
    @Provides
    fun provideQuestionApi(): QuestionApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionApi::class.java)
    }
}