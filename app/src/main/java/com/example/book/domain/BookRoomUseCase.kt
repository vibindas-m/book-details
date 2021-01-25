package com.example.book.domain

import com.example.book.room.BookData
import com.example.book.room.BookRoom

class BookRoomUseCase(
    private val bookRoom: BookRoom
) {
    fun getSavedBookList(): List<BookData>? {
        return bookRoom.getAll()
    }

    fun saveBookData(
        bookDataList: List<BookData>
    ) {
        bookRoom.insert(bookDataList)
    }

    fun deleteBookData(bookData: BookData) {
        bookRoom.delete(bookData)
    }
}