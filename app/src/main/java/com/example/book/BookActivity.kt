package com.example.book

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.book.ui.main.BookViewModel
import org.koin.android.ext.android.get

class BookActivity : AppCompatActivity() {

    private val viewModel: BookViewModel = get()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_activity)
    }
}