package com.example.book.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.book.data.BookListRequest
import com.example.book.data.BookListResponse
import com.example.book.model.Cancellable
import com.example.book.model.Response
import com.example.book.model.UseCase
import com.example.book.service.BookRepo
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import com.example.book.model.Result
import com.example.book.room.BookData

class GetBookListFromStorageUseCase(private val bookRoomUseCase: BookRoomUseCase) :
    UseCase<String, LiveData<Result<List<BookData>>>>,
    CoroutineScope,
    Cancellable {
    var job: Job? = null
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun execute(params: String): LiveData<Result<List<BookData>>> {
        val result = MutableLiveData<Result<List<BookData>>>()
        result.postValue(Result.Loading)
        job = launch {
            val response = bookRoomUseCase.getSavedBookList()
            val toPost = when {
                !response.isNullOrEmpty() -> Result.Success(response)
                else -> Result.Failure("No data, Plz connect your internet and try again")
            }
            result.postValue(toPost)
        }
        return result
    }

    override fun cancel() {
        if (coroutineContext.isActive)
            job?.cancel()
    }
}