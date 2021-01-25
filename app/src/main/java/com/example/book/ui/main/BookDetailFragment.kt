package com.example.book.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.book.R
import kotlinx.android.synthetic.main.fragment_book_detail.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class BookDetailFragment : Fragment() {
    private val viewModel: BookViewModel by sharedViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindUI()
    }

    private fun bindUI() {
        viewModel.getSelectedBook()?.let { bookData ->
            title.text = bookData.title
            description.text = bookData.description
            author.text = String.format("Author: %s", bookData.author)
            price.text = String.format("Price: %s", bookData.price)
            publisher.text = String.format("Publisher: %s", bookData.author)
            buyBt.setOnClickListener {
                openUrl(bookData.amazonProductUrl)
            }
        }

    }

    private fun openUrl(amazonProductUrl: String?) {
        amazonProductUrl?.let {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(browserIntent)
        }
    }

}