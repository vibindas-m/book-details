package com.example.book.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(BookData::class), version = 1)
abstract class BookDB : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        fun getBookDB(context: Context): BookDB {
            synchronized(this) {
                return Room.databaseBuilder(
                    context,
                    BookDB::class.java, "book_database"
                ).build()
            }
        }
    }

}

