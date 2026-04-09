package com.vaxinterview.bookman.data

import com.vaxinterview.bookman.data.local.BookDao
import com.vaxinterview.bookman.data.local.BookItem
import com.vaxinterview.bookman.data.remote.BookAPIService
import javax.inject.Inject

/**
 * This class gets database and service injected and handles the biz logic for fetching data from either.
 * The approach here will be to use the local db as the source of truth for faster loading.  Will fetch
 * new data on app load or via manual trigger (pull to refresh)
 */

class BookRepository @Inject constructor(
    private val bookApi: BookAPIService,
    private val bookDao: BookDao
) {

    //local room db query's
    fun getDAOBooks() = bookDao.getAllBooks()
    fun getDAOBookItem(id: Long) = bookDao.getBookItem(id)
    suspend fun updateBook(id: Long, marked: Boolean) = bookDao.updateBook(id, marked)

    //function to get books from the service api and insert/update into database
    //state will update when underlying data updates
    suspend fun fetchBooks(): Boolean{
        val books = bookApi.getBooks()
        bookDao.updateOrInsert(books)
        return true
    }
}