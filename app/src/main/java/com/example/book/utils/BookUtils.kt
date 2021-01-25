package com.example.book.utils

import com.example.book.data.BookObject
import com.example.book.room.BookData

object BookUtils {
    fun getBookDataListFromResponse(bookObject: List<BookObject>?): List<BookData> {
        val bookDataList: ArrayList<BookData> = arrayListOf()
        bookObject?.map {
            bookDataList.add(getBookDataFromResponseObject(it))
        }
        return bookDataList
    }

    private fun getBookDataFromResponseObject(bookObject: BookObject): BookData {
        val bookDetails = bookObject.book_details?.firstOrNull()
        return BookData(
            title = bookDetails?.title ?: "",
            description = bookDetails?.description ?: "",
            author = bookDetails?.author ?: "",
            price = bookDetails?.price ?: "",
            publisher = bookDetails?.publisher ?: "",
            amazonProductUrl = bookObject.amazon_product_url
        )
    }
}