package com.example.book.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_data")
data class BookData(
    @PrimaryKey
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "price") val price: String?,
    @ColumnInfo(name = "publisher") val publisher: String?,
    @ColumnInfo(name = "amazon_product_url") val amazonProductUrl: String?,
)