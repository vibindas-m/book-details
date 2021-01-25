package com.example.book

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.book.data.BookListResponse
import com.example.book.domain.BookListUseCase
import com.example.book.domain.GetBookListFromStorageUseCase
import com.example.book.domain.SaveBookListToStorageUseCase
import com.example.book.room.BookData
import com.example.book.ui.main.BookViewModel
import com.example.book.utils.BookUtils
import com.google.gson.Gson
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.BufferedReader
import java.io.FileReader
import java.io.Reader

class BookViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    lateinit var viewModel: BookViewModel

    private val getBookListUseCase = mockk<BookListUseCase>(relaxed = true)
    private val getBookListFromStorageUseCase = mockk<GetBookListFromStorageUseCase>(relaxed = true)
    private val saveBookListToStorageUseCase = mockk<SaveBookListToStorageUseCase>(relaxed = true)

    @Before
    fun setUp() {
        viewModel = BookViewModel(
            getBookListUseCase,
            getBookListFromStorageUseCase,
            saveBookListToStorageUseCase
        )
    }

    @Test
    fun updateBookListTest() {
        Assert.assertEquals(null, viewModel.bookDetailsList.value)
        viewModel.updateBookList(null)
        Assert.assertEquals(null, viewModel.bookDetailsList.value)
        val responseData = getOrderResponse()
        viewModel.updateBookList(BookUtils.getBookDataListFromResponse(responseData?.results))
        Assert.assertEquals(
            BookUtils.getBookDataListFromResponse(responseData?.results),
            viewModel.bookDetailsList.value
        )
    }

    @Test
    fun updateBookListResponseTest() {
        Assert.assertEquals(null, viewModel.bookDetailsList.value)
        viewModel.updateBookListResponse(null)
        Assert.assertEquals(arrayListOf<BookData>(), viewModel.bookDetailsList.value)
        val responseData = getOrderResponse()
        viewModel.updateBookListResponse(responseData)
        Assert.assertEquals(
            BookUtils.getBookDataListFromResponse(responseData?.results),
            viewModel.bookDetailsList.value
        )
    }

    @Test
    fun updateSelectedBookTest() {
        Assert.assertEquals(null, viewModel.getSelectedBook())
        val bookData = BookData(title = "THE VANISHING HALF",
            description = "The lives of twin sisters who run away from a Southern Black community at age 16 diverge as one returns and the other takes on a different racial identity but their fates intertwine.",
            author = "Brit Bennett", price = "0", publisher = "Riverhead",
            amazonProductUrl = "https://www.amazon.com/dp/0525536299?tag=NYTBSREV-20&tag=NYTBSREV-20")
        viewModel.updateSelectedBook(bookData)
        Assert.assertEquals(bookData, viewModel.getSelectedBook())
    }

    private fun getOrderResponse(): BookListResponse? {
        val bufferReader =
            BufferedReader(FileReader("../app/src/test/java/mock/bookList.json") as Reader?)
        return Gson().fromJson(bufferReader, BookListResponse::class.java)
    }

}