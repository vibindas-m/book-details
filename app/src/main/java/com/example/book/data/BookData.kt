package com.example.book.data

data class BookListRequest(
    val list: String
)

data class BookListResponse(
    val status: String?,
    val copyright: String?,
    val num_results: String?,
    val results: List<BookObject>?,
    val errors: List<String>
)

data class BookObject(
    val list_name: String?,
    val display_name: String?,
    val bestsellers_date: String?,
    val published_date: String?,
    val rank: String?,
    val amazon_product_url: String?,
    val isbns: List<ISBNSData>?,
    val book_details: List<BookDetails>?,
    val reviews: List<Reviews>
)

data class Reviews(
    val book_review_link: String?,
    val first_chapter_link: String?,
    val sunday_review_link: String?,
    val article_chapter_link: String
)

data class BookDetails(
    val title: String?,
    val description: String?,
    val contributor: String?,
    val author: String?,
    val contributor_note: String?,
    val price: String?,
    val publisher: String?,
    val age_group: String?
)

data class ISBNSData(
    val isbn10: String?,
    val isbn13: String?
)