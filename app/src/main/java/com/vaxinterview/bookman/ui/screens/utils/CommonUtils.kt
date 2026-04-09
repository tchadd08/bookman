package com.vaxinterview.bookman.ui.screens.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun LocalDateTime.toNiceString(): String{
    val prettyDateFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' h:mma z")
    return this.atZone(ZoneId.systemDefault()).format(prettyDateFormat)
}