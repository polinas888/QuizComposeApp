package com.example.quizcomposeapp.data

data class DataResult<T, Boolean, Exception>(
    var data: T? = null,
    var loading: Boolean? = null,
    var exception: Exception? = null
)
