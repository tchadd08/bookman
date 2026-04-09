package com.vaxinterview.bookman.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaxinterview.bookman.data.BookRepository
import com.vaxinterview.bookman.data.local.BookItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val bookRepo : BookRepository
): ViewModel() {

    //using mutablestateflow instead of stateIn for future filtering options if I get time.
    private val _uiState = MutableStateFlow<ListUIState>(ListUIState.Loading)
    val uiState: StateFlow<ListUIState> = _uiState.asStateFlow()

    init{
        //fetch local room database books
        getLatestBooks()
        //send off service call to get latest book data
        getLatestFromService()
    }

    fun getLatestFromService(){
        viewModelScope.launch {
            //update success state if needed for refreshing
            _uiState.update { state ->
                if(state is ListUIState.Success) state.copy(isRefreshing = true)
                else ListUIState.Loading
            }

            //call service with exception catch
            try{
                bookRepo.fetchBooks()
                if (_uiState.value is ListUIState.Success)
                _uiState.update { state ->
                    (state as ListUIState.Success).copy(
                        isRefreshing = false,
                        error = "")
                }

            }catch (e: Exception){
                _uiState.update { state ->
                    if(state is ListUIState.Success) state.copy(isRefreshing = false, error = "Network Error")
                    else ListUIState.Error(e.message ?: "Network request error")
                }
            }
        }
    }

    fun getLatestBooks(){
        viewModelScope.launch {
            _uiState.value = ListUIState.Loading

            bookRepo.getDAOBooks().collect { books->
                _uiState.value = ListUIState.Success(books.map { it.toListUiModel() })
            }
        }
    }
}

fun BookItem.toListUiModel(): ListUiModel {
    return ListUiModel(
        id = this.id,
        titleDisplay = this.title?: "Unknown Title",
        authorDisplay = "By " + (this.author?:"Unknown Author"),
        available = (this.status?.id == 1),
        bookmarked = this.bookmarked
    )
}

sealed interface ListUIState {
    object Loading : ListUIState

    data class Success(
        val data: List<ListUiModel>,
        val isRefreshing: Boolean = false,
        //this message is for service failure
        val error: String = ""
    ) : ListUIState

    data class Error(
        val message: String
    ) : ListUIState
}