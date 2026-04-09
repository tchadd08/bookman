package com.vaxinterview.bookman.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vaxinterview.bookman.R
import com.vaxinterview.bookman.ui.viewmodels.BooksViewModel
import com.vaxinterview.bookman.ui.viewmodels.ListUIState


@Composable
fun BookListScreen(
    onBookClick: (Long) -> Unit,
    viewModel: BooksViewModel = hiltViewModel()
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when(state){
        is ListUIState.Loading -> CircularProgressIndicator()
        is ListUIState.Success -> {
            val books = (state as ListUIState.Success).data
            val error = (state as ListUIState.Success).error
            PullToRefreshBox(isRefreshing =(state as ListUIState.Success).isRefreshing,
                onRefresh = {
                    viewModel.getLatestFromService()
                }) {
                LazyColumn {
                    if(error.isNotEmpty()){
                        item {
                            Text(
                                text = error,
                                modifier = Modifier.padding(bottom = 2.dp, top = 2.dp)
                                    .fillMaxWidth(),
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Red,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    items(books) { book ->
                        Column(modifier = Modifier.fillMaxWidth().clickable {
                            onBookClick(book.id)
                        }) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Column(Modifier.weight(1f)) {
                                    Text(
                                        text = book.titleDisplay,
                                        modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = book.authorDisplay,
                                        modifier = Modifier.padding(
                                            start = 16.dp,
                                            top = 4.dp,
                                            bottom = 16.dp
                                        ),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }

                                //extracting small chunk to for easy testing
                                BookListBookMark(book.bookmarked, Modifier.align(Alignment.CenterVertically))
                            }
                            HorizontalDivider(
                                thickness = 1.dp
                            )
                        }

                    }
                }
            }
        }
        is ListUIState.Error -> Text(text = "Error: ${(state as ListUIState.Error).message}", Modifier.safeContentPadding())
    }

}

@Composable
fun BookListBookMark(
    isBookmarked: Boolean,
    modifier: Modifier
) {
    if(isBookmarked) {
        Icon(
            painter = painterResource(id = R.drawable.bookmark_filled),
            contentDescription = "bookmarked list item",
            tint = Color.Unspecified,
            modifier = modifier.size(32.dp)
                .padding(end = 16.dp)
                .testTag("listbookmark")
        )
    }
}
