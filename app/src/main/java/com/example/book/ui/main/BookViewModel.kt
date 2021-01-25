package com.example.book.ui.main

import androidx.lifecycle.*
import com.example.book.data.BookListRequest
import com.example.book.data.BookListResponse
import com.example.book.domain.BookListUseCase
import com.example.book.domain.BookRoomUseCase
import com.example.book.domain.GetBookListFromStorageUseCase
import com.example.book.domain.SaveBookListToStorageUseCase
import com.example.book.model.Event
import com.example.book.model.Result
import com.example.book.room.BookData
import com.example.book.utils.BookUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class BookViewModel(
    private val bookListUseCase: BookListUseCase,
    private val getBookListFromStorageUseCase: GetBookListFromStorageUseCase,
    private val saveBookListToStorageUseCase: SaveBookListToStorageUseCase
) : ViewModel(), KoinComponent {

    internal val bookListEventTrigger = MutableLiveData<Event<Unit>>()
    val bookListEventEvent: LiveData<Result<BookListResponse>> =
        Transformations.switchMap(bookListEventTrigger) {
            it.getContentIfNotHandled()?.let {
                bookListUseCase.execute(getBookListRequest())
            }
        }

    internal val getBookListFromStorageTrigger = MutableLiveData<Event<Unit>>()
    val getBookListFromStorageEvent: LiveData<Result<List<BookData>>> =
        Transformations.switchMap(getBookListFromStorageTrigger) {
            it.getContentIfNotHandled()?.let {
                getBookListFromStorageUseCase.execute("")
            }
        }

    internal val saveBootDataStorageTrigger = MutableLiveData<Event<List<BookData>>>()
    val saveBootDataStorageEvent: LiveData<Result<Boolean>> =
        Transformations.switchMap(saveBootDataStorageTrigger) {
            it.getContentIfNotHandled()?.let { bookList ->
                saveBookListToStorageUseCase.execute(bookList)
            }
        }

    private var _bookDetailsList: MutableLiveData<List<BookData>> = MutableLiveData()
    val bookDetailsList: LiveData<List<BookData>>
        get() = _bookDetailsList

    private var selectedBook: BookData? = null

    private fun getBookListRequest(): BookListRequest {
        return BookListRequest(list = "hardcover-fiction")
    }

    fun updateBookListResponse(data: BookListResponse?) {
        val bookDataList = BookUtils.getBookDataListFromResponse(data?.results)
        updateBookList(bookDataList)
    }

    fun updateBookList(list: List<BookData>?) {
        _bookDetailsList.value = list
    }

    fun updateSelectedBook(bookData: BookData) {
        selectedBook = bookData
    }

    fun getSelectedBook(): BookData? {
        return selectedBook
    }

}