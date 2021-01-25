package com.example.book

import com.example.book.data.BookListResponse
import com.example.book.room.BookData
import com.example.book.utils.BookUtils
import com.google.gson.Gson
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test
import java.io.BufferedReader
import java.io.FileReader
import java.io.Reader

class BookUtilsTest {
    @Test
    fun getBookDataListFromResponseTest() {
        Assert.assertEquals(arrayListOf<BookData>(), BookUtils.getBookDataListFromResponse(null))
        Assert.assertEquals(
            arrayListOf<BookData>(),
            BookUtils.getBookDataListFromResponse(mockk(relaxed = true))
        )


        Assert.assertEquals(
            BookData(title = "THE VANISHING HALF",
            description = "The lives of twin sisters who run away from a Southern Black community at age 16 diverge as one returns and the other takes on a different racial identity but their fates intertwine.",
            author = "Brit Bennett", price = "0", publisher = "Riverhead", amazonProductUrl = "https://www.amazon.com/dp/0525536299?tag=NYTBSREV-20&tag=NYTBSREV-20"),
            BookUtils.getBookDataListFromResponse(getOrderResponse()?.results).first()
        )
    }

    private fun getOrderResponse(): BookListResponse? {
        val bufferReader = BufferedReader(FileReader("../app/src/test/java/mock/bookList.json") as Reader?)
        return Gson().fromJson(bufferReader, BookListResponse::class.java)
    }
}