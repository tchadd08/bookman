package com.vaxinterview.bookman.data.remote.utils

import com.squareup.moshi.FromJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class DateAdapter {

    private val dateTypes = listOf(
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS'Z'"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
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

}