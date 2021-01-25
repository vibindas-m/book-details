package com.example.book.room

import androidx.annotation.WorkerThread

class BookRoom constructor(private val bookDao: BookDao) {
    @WorkerThread
    fun insert(bookDataList: List<BookData>) {
        bookDao.insertAll(bookDataList)
    }

    @WorkerThread
    fun getAll(): List<BookData> {
        return bookDao.getAll()
    }

    @WorkerThread
    fun delete(bookData: BookData) {
        return bookDao.delete(bookData)
    }
}