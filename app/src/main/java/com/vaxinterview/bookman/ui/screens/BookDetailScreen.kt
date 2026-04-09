package com.vaxinterview.bookman.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vaxinterview.bookman.R
import com.vaxinterview.bookman.ui.viewmodels.DetailUIState
import com.vaxinterview.bookman.ui.viewmodels.DetailViewModel



@Composable
fun BookDetailScreen(viewModel: DetailViewModel = hiltViewModel()) {
    val state by viewModel.detailState.collectAsStateWithLifecycle()

    when(state) {
        is DetailUIState.Loading -> CircularProgressIndicator()
        is DetailUIState.Success -> {
            val book = (state as DetailUIState.Success).data
            Box() {
                Column( modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally){

                    Icon(
                        painter = painterResource(id =
                            if(book.bookmarked) R.drawable.bookmark_filled else R.drawable.bookmark_outline),
                        contentDescription = "Bookmark",
                        tint = Color.Unspecified,
                        modifier = Modifier.clickable{
                            viewModel.updateBookmark(!book.bookmarked)
                        }.size(48.dp)
                    )

                    Text(text = book.titleDisplay,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp))

                    Text(text = book.authorDisplay,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp))

                    Column(modifier = Modifier.border(
                        width = 2.dp,
                        color = if(book.available)Color.Green else Color.Red,
                        shape = RoundedCornerShape(4.dp)
                    ).padding(8.dp)) {
                        Text(
                            text = book.statusDisplay,
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(top =16.dp, bottom = 8.dp),
                        )
                        Text(
                            text = book.checkDisplay,
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(2.dp)
                        )
                        if(!book.available){
                            Text(
                                text = book.dueBack,
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.padding(2.dp)
                            )
                        }
                    }

                    Text(
                        text = book.costDisplay,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )

                    Text(
                        text = book.lastUpdated,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(8.dp)
                    )
                }


            }

        }

        is DetailUIState.Error -> Text(
            text = "Error: ${(state as DetailUIState.Error).message}",
            Modifier.safeContentPadding()
        )
    }
}