package com.vaxinterview.bookman.data.local.utils

import androidx.room.TypeConverter
import com.vaxinterview.bookman.data.local.BookStatus
import com.vaxinterview.bookman.di.CoreModule.moshi
import java.time.LocalDateTime

class BookTypeConverters {
    @TypeConverter
    fun dateFromString(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun dateToString(date: LocalDateTime?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun statusToString(status: BookStatus): String {
       return moshi.adapter(BookStatus::class.java).toJson(status)
    }

    @TypeConverter
    fun statusFromString(stringStatus: String?): BookStatus? {
        return stringStatus?.let { moshi.adapter(BookStatus::class.java).fromJson(it) }
    }
}
