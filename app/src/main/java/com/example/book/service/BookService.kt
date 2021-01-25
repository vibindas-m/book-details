package com.example.book.service


import com.example.book.data.BookListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    //https://api.nytimes.com/svc/books/v3/lists.json?list=hardcover-fiction&api-key=0HHEqCqHljshNZqcwnzo2oDGFrB0ZUGE
    @GET("lists.json")
    fun getBookList(@Query("list") list: String): Call<BookListResponse>

}