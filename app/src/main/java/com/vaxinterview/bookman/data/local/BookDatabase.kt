package com.vaxinterview.bookman.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vaxinterview.bookman.data.local.utils.BookTypeConverters

@Database(entities = [BookItem::class], version = 1)
@TypeConverters(BookTypeConverters::class)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}