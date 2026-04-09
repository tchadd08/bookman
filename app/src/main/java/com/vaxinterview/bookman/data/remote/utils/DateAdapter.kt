package com.vaxinterview.bookman.data.remote.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class DateAdapter {

    private val dateTypes = listOf(
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS'Z'"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),
        DateTimeFormatter.ISO_LOCAL_DATE_TIME
    )

    @FromJson
    fun fromJson(jsonDate: String): LocalDateTime?{
        for(format in dateTypes){
            try{
                return LocalDateTime.parse(jsonDate, format)
            }catch(e: DateTimeParseException) {
                //nothing to do, try next
            }
        }
        return null
    }

    @ToJson
    fun toJson(value: LocalDateTime): String {
        return value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

}