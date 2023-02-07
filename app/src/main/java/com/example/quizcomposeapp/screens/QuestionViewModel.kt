package com.example.quizcomposeapp.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizcomposeapp.data.DataResult
import com.example.quizcomposeapp.model.QuestionItem
import com.example.quizcomposeapp.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val repository: QuestionRepository) :
    ViewModel() {
    val listOfQuestionsStateResult: MutableState<DataResult<ArrayList<QuestionItem>, Boolean, Exception>> =
        mutableStateOf(DataResult(null, true, Exception("")))

    init {
        getQuestions()
    }

    private fun getQuestions() {
        viewModelScope.launch {
            listOfQuestionsStateResult.value.loading = true
            listOfQuestionsStateResult.value = repository.getListOfQuestions()
            listOfQuestionsStateResult.value.loading = false
        }
    }
}