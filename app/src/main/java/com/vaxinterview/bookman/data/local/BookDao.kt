package com.vaxinterview.bookman.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    //Get all books, this would be a limit/paging situation if dataset was larger
    @Query("SELECT * FROM books")
    fun getAllBooks(): Flow<List<BookItem>>

    //Get single entry using primary key
    @Query("SELECT * FROM books WHERE id = :bookId")
    fun getBookItem(bookId: Long): Flow<BookItem?>

    //using ignore for conflicts, we want existing entries to be updated not replaced
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBooks(books: List<BookItem>) : List<Long>

    //Update single entry bookmark field, could use get, copy, update, but this is faster
    @Query("UPDATE books SET bookmarked = :marked WHERE id = :id")
    suspend fun updateBook(id: Long, marked: Boolean)

    //update existing fields with for pre-determined fields/col
    @Update(entity = BookItem::class)
    suspend fun updateBook(scopedBook: ScopedBookItem)

    @Transaction
    suspend fun updateOrInsert(bookItems: List<BookItem>){
        val results = addBooks(bookItems)

        //iterate through results of attempted insert and update failures
        results.forEachIndexed { index, value ->
            if(value == -1L){
                val book = bookItems[index]
                updateBook(ScopedBookItem(book.id,book.title,book.author,book.status,book.fee,book.lastEdited))
            }
        }
    }
}