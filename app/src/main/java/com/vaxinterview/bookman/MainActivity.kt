package com.vaxinterview.bookman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vaxinterview.bookman.ui.screens.BookDetailScreen
import com.vaxinterview.bookman.ui.screens.BookListScreen
import com.vaxinterview.bookman.ui.screens.components.BookTopBar
import com.vaxinterview.bookman.ui.theme.BookmanTheme



import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookmanTheme {
                BooksApp()
            }
        }
    }
}


//Navigation framework even though its only two screens
//keeping it here for simplicity
@Composable
fun BooksApp() {
    val navcontroller = rememberNavController()
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        BookTopBar(navcontroller)
    }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navcontroller, startDestination = "booklist") {
                composable("booklist") {
                    BookListScreen(onBookClick = { id ->
                        navcontroller.navigate("bookdetails/$id")
                    })
                }
                composable("bookdetails/{bookId}", arguments = listOf(navArgument("bookId"){
                    type = NavType.LongType
                })) { BookDetailScreen() }
            }
        }
    }


}
