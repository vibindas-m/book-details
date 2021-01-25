package com.example.book.di

import com.example.book.domain.BookListUseCase
import com.example.book.domain.BookRoomUseCase
import com.example.book.domain.GetBookListFromStorageUseCase
import com.example.book.domain.SaveBookListToStorageUseCase
import com.example.book.room.BookDB
import com.example.book.room.BookRoom
import com.example.book.service.ApiFactory
import com.example.book.service.BookRepo
import com.example.book.service.BookService
import com.example.book.ui.main.BookViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val bookModule = module {

    single {
        ApiFactory.retrofit().create(BookService::class.java)
    }

    factory {
        BookRepo(get())
    }


    single { BookDB.getBookDB(androidContext()) }
    single {
        val bookDB: BookDB = get()
        bookDB.bookDao()
    }
    single { BookRoom(get()) }
    single { BookRoomUseCase(get()) }
    single { BookListUseCase(get()) }
    single { GetBookListFromStorageUseCase(get()) }
    single { SaveBookListToStorageUseCase(get()) }

    viewModel {
        BookViewModel(get(), get(), get())
    }
}