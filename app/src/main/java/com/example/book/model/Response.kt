package com.example.book.model

sealed class Response<out T: Any> {
    data class Success<out T : Any>(val data: T) : Response<T>()
    data class Error(val errorMsg: String) : Response<Nothing>()
}