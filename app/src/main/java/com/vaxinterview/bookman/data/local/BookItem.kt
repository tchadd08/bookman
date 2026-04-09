package com.vaxinterview.bookman.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

/**
 Simple data class annotated for Room table usage and mapped to expected JSON types
 **/

@Entity(tableName = "books")
@JsonClass(generateAdapter = true)
data class BookItem(
    @PrimaryKey val id: Long,
    val title: String?,
    val author: String?,
    val status: BookStatus?,
    //BigDecimal type adapter would be best here for precision when dealing with currencies
    val fee: Double,
    //Date to-from converter required here (and status class)
    val lastEdited: LocalDateTime?,
    //non service property, only for db creation and managing locally
    val bookmarked: Boolean = false
)

@JsonClass(generateAdapter = true)
data class BookStatus(
    val id: Int?, //appears to have 0 or 1 depending on if its checked out or in
    val displayText: String?,
    val timeCheckedIn: LocalDateTime?,
    val timeCheckedOut: LocalDateTime?,
    val dueDate: LocalDateTime?
)


//scoped bookitem class is for updating local database, mostly to not overwrite local only
// fields like bookmark. A compare and change, pre-db, may be better than this route.
data class ScopedBookItem(
    val id: Long,
    val title: String?,
    val author: String?,
    val status: BookStatus?,
    val fee: Double,
    val lastEdited: LocalDateTime?
)