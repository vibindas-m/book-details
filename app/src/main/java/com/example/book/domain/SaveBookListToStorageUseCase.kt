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
import java.lang.Exception

class SaveBookListToStorageUseCase(private val bookRoomUseCase: BookRoomUseCase) :
    UseCase<List<BookData>, LiveData<Result<Boolean>>>,
    CoroutineScope,
    Cancellable {
    var job: Job? = null
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun execute(params: List<BookData>): LiveData<Result<Boolean>> {
        val result = MutableLiveData<Result<Boolean>>()
        result.postValue(Result.Loading)
        job = launch {
            val toPost = try {
                val response = bookRoomUseCase.saveBookData(params)
                Result.Success(true)
            } catch (e: Exception) {
                Result.Failure("Data not Saved")
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