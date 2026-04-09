package com.vaxinterview.bookman.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaxinterview.bookman.data.BookRepository
import com.vaxinterview.bookman.data.local.BookItem
import com.vaxinterview.bookman.ui.screens.utils.toNiceString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val bookRepo : BookRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val bookId : Long = checkNotNull(savedStateHandle["bookId"])

    val detailState: StateFlow<DetailUIState> = bookRepo.getDAOBookItem(bookId)
        .map {
            if(it == null){
                DetailUIState.Error("Unable to find book detail")
            }else{
                DetailUIState.Success(it.toBookUiModel())
            }

        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DetailUIState.Loading
        )

    fun updateBookmark(mark: Boolean){
        viewModelScope.launch {
            bookRepo.updateBook(bookId,mark)
        }

    }
}

fun BookItem.toBookUiModel(): DetailUiModel{
    return DetailUiModel(
        id = this.id,
        titleDisplay = this.title?:"Unknown Title",
        authorDisplay = "By " + (this.author ?: "Unknown Author"),
        statusDisplay = "This book is " + (this.status?.displayText?:""),
        available = (this.status?.id == 1),
        costDisplay = "Late fee: $" + this.fee.toString(),
        lastUpdated = "Last update on " + (this.lastEdited?.toNiceString()?:""),
        bookmarked = this.bookmarked,
        checkDisplay = if(this.status?.id ==1) {
            "Checked In On " + (this.status.timeCheckedIn?.toNiceString()?:"")
        }
        else
        {
            "Checked Out On " + (this.status?.timeCheckedOut?.toNiceString()?:"")
        },
        dueBack = if(this.status?.id != 1) "Due back on " + (this.status?.dueDate?.toNiceString()?:"")
        else ""
    )
}

sealed interface DetailUIState {
    //loading not really needed for such short time but if update ability is added it will be here
    object Loading : DetailUIState

    data class Success(
        val data: DetailUiModel
    ) : DetailUIState

    data class Error(
        val message: String
    ) : DetailUIState
}

