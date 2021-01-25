package com.example.book.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.book.R
import com.example.book.room.BookData
import com.google.android.material.card.MaterialCardView

class BookListListAdapter : RecyclerView.Adapter<BookListListAdapter.BookListHolder>() {

    lateinit var listener: OnItemClickListener
    private lateinit var items: List<BookData>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.book_list_item, parent, false)
        return BookListHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BookListHolder, position: Int) {
        val bookDetails = items[position]

        holder.titleText.text = bookDetails.title
        holder.authorText.text = bookDetails.author
        holder.cardView.setOnClickListener {
            listener.onClick(it, bookDetails)
        }
    }

    fun setData(list: List<BookData>) {
        this.items = list
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClick(view: View, data: BookData)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class BookListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.bookTitle)
        val authorText: TextView = itemView.findViewById(R.id.author)
        val cardView: MaterialCardView = itemView.findViewById(R.id.bookListCard)
    }
}




