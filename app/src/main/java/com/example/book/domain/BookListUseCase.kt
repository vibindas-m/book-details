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

class BookListUseCase(private val bookRepo: BookRepo) : UseCase<BookListRequest,
        LiveData<Result<BookListResponse>>>,
    CoroutineScope,
    Cancellable {
    var job: Job? = null
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun execute(params: BookListRequest): LiveData<Result<BookListResponse>> {
        val result = MutableLiveData<Result<BookListResponse>>()
        result.postValue(Result.Loading)
        job = launch {
            val toPost = when (val response = bookRepo.getBookList(params)) {
                is Response.Success -> {
                    Result.Success(response.data)
                }
                is Response.Error -> {
                    Result.Failure(response.errorMsg)
                }
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