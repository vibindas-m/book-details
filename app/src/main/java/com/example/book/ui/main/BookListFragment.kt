package com.example.book.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.book.R
import com.example.book.adapter.BookListListAdapter
import com.example.book.data.BookListResponse
import com.example.book.model.Event
import com.example.book.model.Result
import com.example.book.room.BookData
import kotlinx.android.synthetic.main.book_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class BookListFragment : Fragment() {

    private val viewModel: BookViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.book_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.bookDetailsList.observe(viewLifecycleOwner, Observer {
            it?.let {
                loadBookList(it)
            }
        })
        viewModel.bookListEventEvent.observe(viewLifecycleOwner, bookListObserver)
        viewModel.saveBootDataStorageEvent.observe(viewLifecycleOwner, saveBookListObserver)
        viewModel.getBookListFromStorageEvent.observe(
            viewLifecycleOwner,
            getBookListFromStorageObserver
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBookList()
    }

    private fun saveBookList() {
        viewModel.bookDetailsList.value?.let {
            viewModel.saveBootDataStorageTrigger.postValue(Event(it))
        }
    }

    private fun getBookListFromStorage() {
        viewModel.getBookListFromStorageTrigger.postValue(Event(Unit))
    }

    private fun getBookList() {
        if (isNetworkAvailable()) {
            viewModel.bookListEventTrigger.postValue(Event(Unit))
        } else {
            getBookListFromStorage()
        }
    }

    private val getBookListFromStorageObserver = Observer<Result<List<BookData>>> {
        if (it is Result.Loading) {
            progressBar.show()
        } else {
            progressBar.hide()
            if (it is Result.Success) {
                viewModel.updateBookList(it.data)
            }
            if (it is Result.Failure) {
                showError(it.errorMsg)
            }
        }
    }

    private val bookListObserver = Observer<Result<BookListResponse>> {
        if (it is Result.Loading) {
            progressBar.show()
        } else {
            progressBar.hide()
            if (it is Result.Success) {
                viewModel.updateBookListResponse(it.data)
                saveBookList()
            }
            if (it is Result.Failure) {
                showError(it.errorMsg)
                getBookListFromStorage()
            }
        }
    }

    private val saveBookListObserver = Observer<Result<Boolean>> {
        if (it is Result.Loading) {
            progressBar.show()
        } else {
            progressBar.hide()
        }
    }

    private fun showError(errorMsg: String) {
        errorText.visibility = View.VISIBLE
        errorText.text = errorMsg
    }

    private fun loadBookList(list: List<BookData>) {
        errorText.visibility = View.GONE
        val bookListAdapter = BookListListAdapter()
        bookListAdapter.setData(list)
        bookListAdapter.setOnItemClickListener(object :
            BookListListAdapter.OnItemClickListener {
            override fun onClick(view: View, data: BookData) {
                viewModel.updateSelectedBook(data)
                gotoBookDetails(view)
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = bookListAdapter
    }

    private fun gotoBookDetails(view: View?) {
        view?.findNavController()?.navigate(R.id.action_mainFragment_to_bookDetailFragment)
    }


    private fun isNetworkAvailable(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}