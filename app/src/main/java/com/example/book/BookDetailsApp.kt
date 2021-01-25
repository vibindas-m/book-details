package com.example.book

import android.app.Application
import com.example.book.di.bookModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BookDetailsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {

            androidContext(this@BookDetailsApp)
            modules(
                listOf(
                    bookModule
                )
            )
        }
    }
}