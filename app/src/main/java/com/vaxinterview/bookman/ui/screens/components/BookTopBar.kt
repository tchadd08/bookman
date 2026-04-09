package com.vaxinterview.bookman.ui.screens.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.vaxinterview.bookman.R

@Composable
fun BookTopBar (navController: NavHostController){

    val navBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStack?.destination?.route


    //this top bar is easier to use than making one but wouldn't recommend for production
    @OptIn(ExperimentalMaterial3Api::class)
    TopAppBar(
        title = {
            Text(
                text = when (currentRoute) {
                    "booklist" -> "Library Books"
                    "bookdetails/{bookId}" -> "Book Detail"
                    else -> "Bookman"
                }
            )
        },
        navigationIcon = {
            if(currentRoute != "booklist"){
                IconButton(onClick = {
                    navController.popBackStack()}) {
                    Icon(painter = painterResource(id = R.drawable.arrow_back), contentDescription = "back")
                }

            }
        }
    )

}