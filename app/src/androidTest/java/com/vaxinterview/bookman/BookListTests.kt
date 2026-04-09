package com.vaxinterview.bookman

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vaxinterview.bookman.ui.screens.BookListBookMark
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
class BookmarkTest {

    @get:Rule
    val bookMarkRule = createComposeRule()

    @Test
    fun bookmark_displayed_when_isBookMarked_true() {

        bookMarkRule.setContent { BookListBookMark(true, Modifier) }

        //very simple test to verify bookmark is shown
        bookMarkRule.onNodeWithTag("listbookmark")
            .assertExists()
            .assertIsDisplayed()

    }

    @Test
    fun bookmark_notdisplayed_when_isBookMarked_false() {

        bookMarkRule.setContent { BookListBookMark(false, Modifier) }

        //very simple test to verify bookmark is shown
        bookMarkRule.onNodeWithTag("listbookmark")
            .assertDoesNotExist()

    }
}