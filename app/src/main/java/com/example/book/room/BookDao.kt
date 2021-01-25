package com.example.book.room

import androidx.room.*

@Dao
interface BookDao{
    @Query("SELECT * FROM book_data")
    fun getAll(): List<BookData>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(vararg bookData: BookData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(bookDataList: List<BookData>)

    @Delete
    fun delete(bookData: BookData)

}