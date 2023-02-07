package com.example.quizcomposeapp.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.quizcomposeapp.component.Questions


@Composable
fun QuizHome(questionViewModel: QuestionViewModel = hiltViewModel()) {
    Questions(questionViewModel)
}