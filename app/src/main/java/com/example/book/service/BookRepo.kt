package com.example.book.service

import com.example.book.data.BookListRequest
import com.example.book.data.BookListResponse
import com.example.book.model.Response
import retrofit2.await

class BookRepo constructor(private val bookService: BookService) {

    suspend fun getBookList(bookListRequest: BookListRequest): Response<BookListResponse> {
        return try {
            val result = bookService.getBookList(bookListRequest.list).await()
            with(result) {
                if (this.status == "OK") {
                    Response.Success(this)
                } else {
                    val errorMsg = StringBuilder()
                    this.errors.map {
                        errorMsg.append("$it ")
                    }
                    Response.Error(errorMsg.toString())
                }
            }
        } catch (e: Exception) {
            Response.Error(e.message ?: "")
        }
    }
}