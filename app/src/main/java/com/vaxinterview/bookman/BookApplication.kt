package com.vaxinterview.bookman

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BookApplication : Application() {
    //empty extension of application for hilt usage
}


/**
 * Ideas for nice to haves if time permits
 * 1. Add bigDecimal type adapter for currency precision
 * 2. Sort books by filterable fields
 * 3. Color code over due vs checkout books
 * 4. Write wrapper class to de-couple from room for easier db change
 * 5. Repo interface for easier testing
 * 6. Paging for larger data set
 * 7. Monitor network connection and retry if failed previously
 * 8. Screen based data class with pre-formatted strings
 */

